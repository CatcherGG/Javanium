package com.catcher.javanium.pow;

import java.util.Collections;

import org.apache.commons.codec.binary.Hex;

import com.catcher.javanium.blockchain.block.Block;

public class ProofOfWork {

	public static boolean verify(Block block, int difficulty){
		return Hex.encodeHexString(block.hash()).startsWith(zeroes(difficulty));
	}

	private static String zeroes(int difficulty) {
		return String.join("", Collections.nCopies(difficulty, "0"));
	}


}
