package com.catcher.javanium.ledger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.catcher.javanium.blockchain.transaction.Output;
import com.catcher.javanium.blockchain.transaction.UnspentTransaction;

public class UnspentTransactionsKeyValuePool implements UnspentTransactionsPool {


	Map<UnspentTransaction, Output> unspentTransactionPool = new HashMap<>();


	public UnspentTransactionsKeyValuePool(UnspentTransactionsKeyValuePool otherKeyValue){
		unspentTransactionPool = new HashMap<>(otherKeyValue.unspentTransactionPool);
	}

	@Override
	public void addUnspentTransaction(UnspentTransaction unspentTransaction, Output output) {
		unspentTransactionPool.put(unspentTransaction, output);
	}

	@Override
	public void removeUnspentTransaction(UnspentTransaction unspentTransaction) {
		unspentTransactionPool.remove(unspentTransaction);
	}

	@Override
	public Output getTransactionOutput(UnspentTransaction unspentTransaction) {
		return unspentTransactionPool.get(unspentTransaction);
	}

	@Override
	public List<UnspentTransaction> getAllUnspentTransactions() {
		return new ArrayList<>(unspentTransactionPool.keySet());
	}

	@Override
	public UnspentTransactionsPool copy(UnspentTransactionsPool ledger) {
		if (ledger.getClass().isAssignableFrom(getClass())){
			return new UnspentTransactionsKeyValuePool((UnspentTransactionsKeyValuePool) ledger);
		}
		return null;
	}

	@Override
	public boolean contains(UnspentTransaction unspentTransaction) {
		return unspentTransactionPool.containsKey(unspentTransaction);
	}

	@Override
	public boolean contains(Output transactionOutput) {
		return unspentTransactionPool.containsValue(transactionOutput);
	}

}
