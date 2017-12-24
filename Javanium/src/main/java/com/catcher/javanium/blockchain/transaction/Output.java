package com.catcher.javanium.blockchain.transaction;

import java.security.PublicKey;

import com.catcher.javanium.utilities.BytesConvertor;
import com.google.common.primitives.Bytes;


/**
 * Representing a transaction output.
 * @author GuyGo
 *
 */
public class Output {

	// Amount of coins to transfer
	public final double value;
	// The address to send to.
	public final PublicKey address;

	public Output(double value, PublicKey address) {
		this.value = value;
		this.address = address;
	}

	public byte[] toBytes(){
		return Bytes.concat(BytesConvertor.of(value), address.getEncoded());
	}

}
