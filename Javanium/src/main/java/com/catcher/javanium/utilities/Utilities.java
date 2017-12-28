package com.catcher.javanium.utilities;

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

}
