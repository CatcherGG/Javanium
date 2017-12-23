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

	KeyPairGenerator keyGen = null;
	SecureRandom random = null;

	public RSASignature(){
		try{
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
			Signature sig = Signature.getInstance("SHA256withRSA");
			sig.initVerify(pubKey);
			sig.update(SerializationUtils.serialize(message));
			return sig.verify(signature);
		} catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
			Logger.error(e);
		}
		return false; 
	}

	@Override
	public KeyPair generateKeys(){
		return keyGen.genKeyPair();
	}

	@Override
	public byte[] sign(PrivateKey privateKey, Serializable data){
		try {
			Signature rsa = Signature.getInstance("SHA256withRSA");
			rsa.initSign(privateKey);
			rsa.update(SerializationUtils.serialize(data));
			return rsa.sign();
		} catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
			Logger.error(e);
		}
		return new byte[0];
	}


}
