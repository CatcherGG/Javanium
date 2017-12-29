package com.catcher.javanium.blockchain;

import org.mapdb.HTreeMap;

import com.catcher.javanium.blockchain.block.Block;
import com.catcher.javanium.blockchain.transaction.Transaction;
import com.catcher.javanium.blockchain.transaction.TransactionHandler;
import com.catcher.javanium.blockchain.transaction.TransactionPool;
import com.catcher.javanium.blockchain.unspenttransaction.UnspentTransactionsKeyValuePool;
import com.catcher.javanium.blockchain.unspenttransaction.UnspentTransactionsPool;
import com.catcher.javanium.utilities.DBList;

public class BlockChain {

	public static final int CUT_OFF_AGE = 10;

	TransactionHandler transactionHandler = new TransactionHandler(new UnspentTransactionsKeyValuePool());
	TransactionPool transactionPool;
	HTreeMap<byte[], Block> blockchain;

	/**
	 * create an empty block chain with just a genesis block.
	 */
	public BlockChain(Block genesisBlock) {
		blockchain = DBList.getInstance();
		transactionPool = new TransactionPool();
		blockchain.put(genesisBlock.hash(), genesisBlock);
	}

	/** Get the maximum height block */
	public Block getMaxHeightBlock() {
		return blockchain.get(blockchain.size()-1);
	}

	/** Get the UnspentTransactionsPool for mining a new block on top of max height block */
	public UnspentTransactionsPool getMaxHeightUTXOPool() {
		return transactionHandler.getUnspentTransactionPool();
	}

	/** Get the transaction pool to mine a new block */
	public TransactionPool getTransactionPool() {
		return transactionPool;
	}

	/**
	 * Adds block to the block chain if it is valid.
	 * @return true if block is successfully added
	 */
	public boolean addBlock(Block block) {

		// 1. Block isn't genesis block.
		byte[] prevBlockHash = block.getPrevBlockHash();
		if (prevBlockHash == null){
			return false;
		}
		// 2. Checking parent existence
		if (!blockchain.containsKey(prevBlockHash)){
			return false;
		}
		Block parentBlock = blockchain.get(prevBlockHash);
		// 3. Checking that all transactions are valid
		if (!block.getTransactions().stream().allMatch(transactionHandler::isTransactionValid)) {
			return false;
		}
		// 4. Height is valid
		if (parentBlock.getHeight()+1 <= getMaxHeightBlock().getHeight() - CUT_OFF_AGE) {
			return false;
		}

		transactionHandler.handleTransactions(block.getTransactions().toArray(new Transaction[0]));
		transactionHandler.addCoinbaseTransaction(block.getCoinbase());
		return true;
	}

	/** Add a transaction to the transaction pool */
	public void addTransaction(Transaction transaction) {
		if (transactionHandler.isTransactionValid(transaction)){
			transactionPool.addTransaction(transaction);
		}
	}

}
