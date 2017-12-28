package com.catcher.javanium.blockchain.block.merkletree;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import com.catcher.javanium.utilities.HashFunctionFactory;
import com.catcher.javanium.utilities.HashFunctionFactory.HASH;
import com.catcher.javanium.utilities.Logger;
import com.catcher.javanium.utilities.Utilities;
import com.google.common.hash.HashFunction;
import com.google.common.primitives.Bytes;

public class BitcoinMerkleTree implements MerkleTree<String, String> {

	HashFunction hash;

	public BitcoinMerkleTree(){
		hash = HashFunctionFactory.getHashFunction(HASH.SHA256);
	}

	@Override
	public String getRoot(String[] transactions){
		try {
			if (transactions.length == 1) {
				return transactions[0];
			}
			String[] hashNodes = new String[((int) Math.ceil(transactions.length/2.0))];
			// Hash each trasactionID with the consecutive transaction ID
			for (int i=0,j=0; i<transactions.length - 1; i+=2,j++){
				hashNodes[j] = getDoubleDigestHexValue(transactions[i], transactions[i+1]);
			}
			// Last node is hashed with itself.
			if (transactions.length % 2 == 1){
				String lastOddNode = transactions[transactions.length-1];
				hashNodes[hashNodes.length -1] = getDoubleDigestHexValue(lastOddNode, lastOddNode);
			}

			return getRoot(hashNodes);
		} catch (DecoderException e){
			Logger.error(e);
		}

		return StringUtils.EMPTY;
	}

	private String getDoubleDigestHexValue(String str1, String str2) throws DecoderException {
		// This takes out two strings decode to bytes, reverse the order (Little endian), Combine them and then hash them twice. [This is how it's really being done in bitcoin.]
		byte[] combinedAndReveresed = Bytes.concat(Utilities.reverseBytes(Hex.decodeHex(str1)), Utilities.reverseBytes(Hex.decodeHex(str2)));
		byte[] oneDigest = hash.hashBytes(combinedAndReveresed).asBytes();
		byte[] secondDigest = hash.hashBytes(oneDigest).asBytes();
		// Lastly, Reverse it back.
		secondDigest = Utilities.reverseBytes(secondDigest);
		return Hex.encodeHexString(secondDigest);
	}


}
