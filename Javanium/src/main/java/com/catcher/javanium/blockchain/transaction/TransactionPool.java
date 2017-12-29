package com.catcher.javanium.blockchain.transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TransactionPool {

	private Map<byte[], Transaction> pool;

	public TransactionPool() {
		pool = new HashMap<>();
	}

	public TransactionPool(TransactionPool transactionPool) {
		pool = new HashMap<>(transactionPool.pool);
	}

	public void addTransaction(Transaction transaction) {
		pool.put(transaction.hash(), transaction);
	}

	public void removeTransaction(byte[] transactionID) {
		pool.remove(transactionID);
	}

	public Transaction getTransaction(byte[] transactionID) {
		return pool.get(transactionID);
	}

	public List<Transaction> getTransactions() {
		return new ArrayList<>(pool.values());
	}

}
