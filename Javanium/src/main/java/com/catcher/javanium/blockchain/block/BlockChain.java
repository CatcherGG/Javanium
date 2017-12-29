package com.catcher.javanium.blockchain.block;

import com.catcher.javanium.blockchain.Block;
import com.catcher.javanium.blockchain.transaction.Transaction;
import com.catcher.javanium.blockchain.transaction.TransactionPool;
import com.catcher.javanium.ledger.UnspentTransactionsPool;

public interface BlockChain {


	/** Get the maximum height block */
	public Block getMaxHeightBlock();

	/** Get the UTXOPool for mining a new block on top of max height block */
	public UnspentTransactionsPool getMaxHeightUTXOPool();

	/** Get the transaction pool to mine a new block */
	public TransactionPool getTransactionPool();

	/**
	 * Add {@code block} to the block chain if it is valid. For validity, all transactions should be
	 * valid and block should be at {@code height > (maxHeight - CUT_OFF_AGE)}.
	 * 
	 * <p>
	 * For example, you can try creating a new block over the genesis block (block height 2) if the
	 * block chain height is {@code <=
	 * CUT_OFF_AGE + 1}. As soon as {@code height > CUT_OFF_AGE + 1}, you cannot create a new block
	 * at height 2.
	 * 
	 * @return true if block is successfully added
	 */
	public boolean addBlock(Block block);

	/** Add a transaction to the transaction pool */
	public void addTransaction(Transaction tx);

}
