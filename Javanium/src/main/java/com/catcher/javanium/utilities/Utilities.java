package com.catcher.javanium.utilities;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.catcher.javanium.blockchain.transaction.Transaction;

public class Utilities {

	private Utilities(){
		throw new IllegalAccessError("Utilities class");
	}

	public static byte[] reverseBytes(byte[] bytes) {
		byte[] reversed = new byte[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			reversed[i] = bytes[bytes.length-1-i];
		}
		return reversed;
	}

	public static byte[][] toBytesArray(Transaction[] transactions) {
		return Arrays.stream(transactions).map(Transaction::hash).collect(Collectors.toList()).toArray(new byte[0][0]);
	}

}
