package com.catcher.javanium.crypto.digitalsignature;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.KeyPair;

import org.junit.Test;

import com.catcher.javanium.crypto.digitalsignature.RSASignature;

public class RSASignatureTest {

	public static RSASignature signature = new RSASignature();

	@Test
	public void verifySignature()  {
		KeyPair keyPair = signature.generateKeys();
		String someStr = "someStr";
		byte[] signed = null;
		signed = signature.sign(keyPair.getPrivate(), someStr);
		assertTrue(signed != null && signed.length > 0);
		assertTrue( signature.verifySignature(keyPair.getPublic(), someStr, signed));
	}

	@Test
	public void verifyNotSignature() {
		KeyPair keyPair = signature.generateKeys();
		String someStr = "someStr";
		assertFalse( signature.verifySignature(keyPair.getPublic(), someStr, new byte[0]));
	}

}
