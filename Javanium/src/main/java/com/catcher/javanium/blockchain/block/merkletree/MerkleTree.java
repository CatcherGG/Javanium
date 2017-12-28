package com.catcher.javanium.blockchain.block.merkletree;

public interface MerkleTree<T, R> {

	/** 
	 * @param transactions
	 * @return Merkle root of the transactions.
	 */
	public R getRoot(T[] transactions);



}
