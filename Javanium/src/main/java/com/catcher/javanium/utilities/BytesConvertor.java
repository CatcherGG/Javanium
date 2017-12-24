package com.catcher.javanium.utilities;

import java.nio.ByteBuffer;

import com.google.common.primitives.Ints;

public class BytesConvertor {

	public static byte[] of(double num){
		return ByteBuffer.allocate(8).putDouble(num).array();
	}

	public static byte[] of(int num){
		return Ints.toByteArray(num);
	}

}
