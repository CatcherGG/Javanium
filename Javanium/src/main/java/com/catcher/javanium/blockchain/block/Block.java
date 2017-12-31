package com.catcher.javanium.blockchain.block;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.catcher.javanium.blockchain.block.merkletree.BasicMerkleTree;
import com.catcher.javanium.blockchain.transaction.CoinBaseTransaction;
import com.catcher.javanium.blockchain.transaction.Transaction;
import com.catcher.javanium.utilities.HashFunctionFactory;
import com.catcher.javanium.utilities.HashFunctionFactory.HASH;
import com.google.common.hash.HashFunction;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Longs;

public class Block implements Serializable {

	private static final long serialVersionUID = 5254886391779744055L;

	public static final double COINBASE_VALUE = 25;

	private byte[] hash;
	private byte[] prevBlockHash;
	private byte[] merkleRoot;
	private long height;
	private Transaction coinbase;
	private List<Transaction> transactions;
	public int nonce;

	/** Creating a block. Pointing to the previous block and creating a new genesis block. */
	public Block(byte[] prevHash, PublicKey address, long height) {
		prevBlockHash = prevHash;
		coinbase = new CoinBaseTransaction(COINBASE_VALUE, address);
		transactions = new ArrayList<>();
		this.height = height;
		merkleRoot = new byte[0];
		hash = new byte[0];
		nonce = 0;
	}

	public Transaction getCoinbase() {
		return coinbase;
	}

	public byte[] getHash() {
		return hash;
	}

	public final long getHeight() {
		return height;
	}

	public byte[] getPrevBlockHash() {
		return prevBlockHash;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public Transaction getTransaction(int index) {
		return transactions.get(index);
	}

	public void addTransaction(Transaction tx) {
		transactions.add(tx);
		merkleRoot = new BasicMerkleTree().getRoot(transactions.toArray(new Transaction[transactions.size()]));
	}

	public byte[] getRawBlock() {

		byte[] rawData = prevBlockHash != null ? prevBlockHash.clone() : new byte[0];
		rawData = Bytes.concat(rawData,
				Longs.toByteArray(height),
				merkleRoot);

		return rawData;
	}


	public byte[] hash() {

		HashFunction hashFunction = HashFunctionFactory.getHashFunction(HASH.SHA512);
		hash = hashFunction.newHasher()
				.putBytes(prevBlockHash)
				.putBytes(merkleRoot)
				.putInt(nonce)
				.hash().asBytes();
		return hash;
	}

	@Override
	public int hashCode() {
		HashFunction hashFunction = HashFunctionFactory.getHashFunction(HASH.SHA512);
		return hashFunction.newHasher()
				.putBytes(prevBlockHash)
				.putBytes(merkleRoot)
				.hash().asInt();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Block other = (Block) obj;
		if (coinbase == null) {
			if (other.coinbase != null) {
				return false;
			}
		} else if (!coinbase.equals(other.coinbase)) {
			return false;
		}
		if (!Arrays.equals(hash, other.hash)) {
			return false;
		}
		if (height != other.height) {
			return false;
		}
		if (!Arrays.equals(merkleRoot, other.merkleRoot)) {
			return false;
		}
		if (!Arrays.equals(prevBlockHash, other.prevBlockHash)) {
			return false;
		}
		if (transactions == null) {
			if (other.transactions != null) {
				return false;
			}
		} else if (!transactions.equals(other.transactions)) {
			return false;
		}
		return true;
	}



}
