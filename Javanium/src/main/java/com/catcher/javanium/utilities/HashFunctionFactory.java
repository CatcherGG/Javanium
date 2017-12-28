package com.catcher.javanium.utilities;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class HashFunctionFactory {

	public enum HASH {
		SHA512, SHA256, MD5
	}

	public static HashFunction getHashFunction(HASH hash){
		switch (hash) {
		case SHA512:
			return Hashing.sha512();
		case SHA256:
			return Hashing.sha256();
		default:
			return Hashing.sha512();
		}
	}

}
