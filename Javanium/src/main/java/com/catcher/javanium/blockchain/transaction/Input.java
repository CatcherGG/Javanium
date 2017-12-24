package com.catcher.javanium.blockchain.transaction;

import java.util.Arrays;

import com.catcher.javanium.utilities.BytesConvertor;
import com.google.common.primitives.Bytes;

public class Input {

	/** hash of the Transaction whose output is being used */
	public byte[] outputTransactionHash;
	/** used output's index in the previous transaction */
	public int outputIndex;
	/** the signature produced to check validity */
	public byte[] signature;

	public Input(byte[] prevHash, int index) {
		this(prevHash, index, null);
	}

	public Input(byte[] prevHash, int index, byte[] sigature) {
		addOutputTransactionHash(prevHash);
		addSignature(sigature);
		outputIndex = index;
	}

	private void addOutputTransactionHash(byte[] prevHash) {
		if (prevHash == null) {
			outputTransactionHash = null;
		}
		else {
			outputTransactionHash = Arrays.copyOf(prevHash, prevHash.length);
		}
	}

	public void addSignature(byte[] sigature) {
		if (sigature == null) {
			signature = null;
		}
		else {
			signature = Arrays.copyOf(sigature, sigature.length);
		}
	}

	public byte[] toBytes(){
		return Bytes.concat(outputTransactionHash, signature, BytesConvertor.of(outputIndex));
	}

}
