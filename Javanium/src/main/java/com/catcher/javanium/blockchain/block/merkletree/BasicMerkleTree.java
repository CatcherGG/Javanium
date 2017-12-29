package com.catcher.javanium.blockchain.block.merkletree;

import com.catcher.javanium.blockchain.transaction.Transaction;
import com.catcher.javanium.utilities.HashFunctionFactory;
import com.catcher.javanium.utilities.HashFunctionFactory.HASH;
import com.catcher.javanium.utilities.Utilities;
import com.google.common.hash.HashFunction;
import com.google.common.primitives.Bytes;

public class BasicMerkleTree implements MerkleTree<Transaction, byte[]> {

	HashFunction hash;
	byte[] merkleRoot;

	public BasicMerkleTree(){
		hash = HashFunctionFactory.getHashFunction(HASH.SHA512);
	}

	@Override
	public byte[] getRoot(Transaction[] transactions){
		if (transactions.length == 0) {
			return new byte[0];
		}
		return getRoot(Utilities.toBytesArray(transactions));
	}

	private byte[] getRoot(byte[][] transactions){
		if (transactions.length == 1) {
			return transactions[0];
		}
		byte[][] hashNodes = new byte[((int) Math.ceil(transactions.length/2.0))][];
		// Hash each trasactionID with the consecutive transaction ID
		for (int i=0,j=0; i<transactions.length - 1; i+=2,j++){
			hashNodes[j] = hashBytes(transactions[i], transactions[i+1]);
		}
		// Last node is hashed with itself.
		if (transactions.length % 2 == 1){
			byte[] lastOddNode = transactions[transactions.length-1];
			hashNodes[hashNodes.length -1] = hashBytes(lastOddNode, lastOddNode);
		}

		return getRoot(hashNodes);
	}

	private byte[] hashBytes(byte[] trans1, byte[] trans2) {
		// This is different from Bitcoin such that I don't reverse, Don't hash twice [For simplicity and readability] - and I'm using SHA512 while Bitcoin uses SHA256.
		// I've implemented Bitcoin merkle tree to show how Bitcoin merkle trees actully work.
		return hash.hashBytes(Bytes.concat(trans1, trans2)).asBytes();
	}


}
