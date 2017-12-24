package com.catcher.javanium.crypto.digitalsignature;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface DigitalSignature {

	public boolean verifySignature(PublicKey pubKey, Serializable message, byte[] signature);

	public KeyPair generateKeys();

	public byte[] sign(PrivateKey privateKey, Serializable data);
}
