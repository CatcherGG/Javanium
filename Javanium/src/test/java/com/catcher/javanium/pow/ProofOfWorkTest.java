package com.catcher.javanium.pow;

import java.security.KeyPair;

import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;

import com.catcher.javanium.blockchain.block.Block;
import com.catcher.javanium.crypto.digitalsignature.RSASignature;

public class ProofOfWorkTest {


	@Test
	public void verifyPow()  {
		RSASignature signature = new RSASignature();
		KeyPair keyPair = signature.generateKeys();
		Block block = new Block(new byte[0], keyPair.getPublic(), 1);
		block.nonce = -970989175;
		String correctHash = "000000f6c4c485cdad6b2e325c9fbbe13485f812581a80725765d08f55dd7a13212dd10ab7f2afc459d05faf0ed02b223c7483dc396ffdf77df0537bcba93549";
		Assert.assertTrue(ProofOfWork.verify(block, 6));
		Assert.assertEquals(correctHash, Hex.encodeHexString(block.hash()));
	}
}
