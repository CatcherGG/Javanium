package com.catcher.javanium.blockchain.transaction;

import java.util.Arrays;

public class UnspentTransaction {

	/** Hash of the transaction from which this UTXO originates */
	private byte[] transactionHash;

	/** Index of the corresponding output in said transaction */
	private int index;

	public UnspentTransaction(byte[] transactionHash, int index) {
		this.transactionHash = Arrays.copyOf(transactionHash, transactionHash.length);
		this.index = index;
	}

	/** @return the transaction hash of this UTXO */
	public byte[] getTransactionHash() {
		return transactionHash;
	}

	/** @return the index of this UTXO */
	public int getIndex() {
		return index;
	} 

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + Arrays.hashCode(transactionHash);
		return result;
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
		UnspentTransaction other = (UnspentTransaction) obj;
		if (index != other.index) {
			return false;
		}
		return Arrays.equals(transactionHash, other.transactionHash);
	}

}
