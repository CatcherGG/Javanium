package com.catcher.javanium.blockchain.block;

import java.security.PublicKey;

import com.catcher.javanium.blockchain.BlockChain;
import com.catcher.javanium.blockchain.transaction.Transaction;
import com.catcher.javanium.blockchain.transaction.TransactionHandler;
import com.catcher.javanium.blockchain.transaction.TransactionPool;
import com.catcher.javanium.blockchain.unspenttransaction.UnspentTransactionsPool;

public class BlockHandler {

	private BlockChain blockChain;

	/** assume blockChain has the genesis block */
	public BlockHandler(BlockChain blockChain) {
		this.blockChain = blockChain;
	}

	/**
	 * add block to the block chain if it is valid.
	 * @return true if the block is valid and has been added, false otherwise
	 */
	public boolean processBlock(Block block) {
		if (block == null) {
			return false;
		}
		return blockChain.addBlock(block);
	}

	/** create a new block over the max height block */
	public Block createBlock(PublicKey myAddress) {
		Block parent = blockChain.getMaxHeightBlock();
		byte[] parentHash = parent.getHash();
		Block current = new Block(parentHash, myAddress, parent.getHeight()+1);
		UnspentTransactionsPool unspentTransactionPool = blockChain.getMaxHeightUTXOPool();
		TransactionPool transactionPool = blockChain.getTransactionPool();
		TransactionHandler handler = new TransactionHandler(unspentTransactionPool);
		Transaction[] transactions = transactionPool.getTransactions().toArray(new Transaction[0]);
		Transaction[] validTransactions = handler.handleTransactions(transactions);
		for (int i = 0; i < validTransactions.length; i++) {
			current.addTransaction(validTransactions[i]);
		}
		current.hash();
		if (blockChain.addBlock(current)) {
			return current;
		} else {
			return null;
		}
	}

	/** process a Transaction */
	public void processTransaction(Transaction transaction) {
		blockChain.addTransaction(transaction);
	}

}
