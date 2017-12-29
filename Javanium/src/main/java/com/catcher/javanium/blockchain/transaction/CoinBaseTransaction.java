package com.catcher.javanium.blockchain.transaction;

import java.security.PublicKey;

public class CoinBaseTransaction extends Transaction {

	private static final long serialVersionUID = -2440461467004794812L;

	/** create a coinbase transaction of value {@code coin} and calls finalize on it */
	public CoinBaseTransaction(double coin, PublicKey address) {
		super();
		addOutput(coin, address);
	}


	@Override
	public boolean isCoinbase(){
		return true;
	}
}
