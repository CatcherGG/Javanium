package com.catcher.javanium.blockchain.transaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.catcher.javanium.blockchain.unspenttransaction.UnspentTransactionsPool;
import com.catcher.javanium.crypto.digitalsignature.DigitalSignature;
import com.catcher.javanium.crypto.digitalsignature.RSASignature;

public class TransactionHandler {

	UnspentTransactionsPool unspentTransactionPool;

	public boolean isTransactionValid(Transaction transaction) {
		DigitalSignature signature = new RSASignature();

		Set<Output> outputsConfirmed = new HashSet<>();

		double previousTransactionSum = 0.0;
		double currentTransactionSum = 0.0;

		List<Input> inputs = transaction.getInputs();
		for (int i=0; i<inputs.size(); i++) {
			Input input = inputs.get(i);
			UnspentTransaction unspentTransaction = new UnspentTransaction(input.outputTransactionHash, input.outputIndex);
			Output output = unspentTransactionPool.getTransactionOutput(unspentTransaction);
			if (unspentTransactionPool.contains(unspentTransaction)	&& !outputsConfirmed.contains(output) && output.value > 0 && signature.verifySignature(output.address, transaction, input.signature)){
				outputsConfirmed.add(output);
				previousTransactionSum += output.value;
			} else {
				return false;
			}
		}

		for (Output output : transaction.getOutputs()){
			if (output.value < 0) {
				return false;
			}
			currentTransactionSum += output.value;
		}

		return previousTransactionSum >= currentTransactionSum;
	}

	public Transaction[] handleTransactions(Transaction[] possibleTransactions) {

		List<Transaction> validTransactions = new ArrayList<>();

		for (Transaction transaction : possibleTransactions) {
			if (isTransactionValid(transaction)){
				validTransactions.add(transaction);

				for (Input input : transaction.getInputs()) {
					UnspentTransaction unspentTransaction = new UnspentTransaction(input.outputTransactionHash, input.outputIndex);
					unspentTransactionPool.removeUnspentTransaction(unspentTransaction);
				}

				List<Output> outputs = transaction.getOutputs();
				for (int i = 0; i < outputs.size(); i++) {
					Output output = transaction.getOutput(i);
					UnspentTransaction unspentTransaction = new UnspentTransaction(transaction.hash(), i);
					unspentTransactionPool.addUnspentTransaction(unspentTransaction, output);
				}
			}
		}

		return validTransactions.toArray(possibleTransactions);
	}

	public void addCoinbaseTransaction(Transaction coinbaseTransaction){
		if (isTransactionValid(coinbaseTransaction)){
			int i = 0;
			for (Output output : coinbaseTransaction.getOutputs()) {
				UnspentTransaction unspentTransaction = new UnspentTransaction(coinbaseTransaction.hash(), i++);
				unspentTransactionPool.addUnspentTransaction(unspentTransaction, output);
			}
		}
	}

	public TransactionHandler(UnspentTransactionsPool pool) {
		unspentTransactionPool = pool.copy(pool);
	}

	public final UnspentTransactionsPool getUnspentTransactionPool() {
		return unspentTransactionPool;
	}


}

