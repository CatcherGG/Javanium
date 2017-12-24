package com.catcher.javanium.crypto.digitalsignatue;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.commons.lang3.SerializationUtils;

import com.catcher.javanium.utilities.Logger;

public class RSASignature implements DigitalSignature {

	Signature rsa = null;
	KeyPairGenerator keyGen = null;
	SecureRandom random = null;

	public RSASignature(){
		try{
			rsa = Signature.getInstance("SHA256withRSA");
			keyGen = KeyPairGenerator.getInstance("RSA");
			random = SecureRandom.getInstance("SHA1PRNG");
			keyGen.initialize(2048, random);
		} catch (NoSuchAlgorithmException e) {
			Logger.error(e);
		}
	}


	@Override
	public boolean verifySignature(PublicKey pubKey, Serializable message, byte[] signature) {
		try {
			rsa.initVerify(pubKey);
			rsa.update(SerializationUtils.serialize(message));
			return rsa.verify(signature);
		} catch (InvalidKeyException | SignatureException e) {
			Logger.error(e);
		}
		return false; 
	}

	@Override
	public KeyPair generateKeys(){
		return keyGen.genKeyPair();
	}

	@Override
	public byte[] sign(PrivateKey privateKey, Serializable data) {
		try {
			rsa.initSign(privateKey);
			rsa.update(SerializationUtils.serialize(data));
			return rsa.sign();
		} catch (SignatureException | InvalidKeyException e) {
			Logger.error(e);
			return new byte[0];
		}
	}


}
