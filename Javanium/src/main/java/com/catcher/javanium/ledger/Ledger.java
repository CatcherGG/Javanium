package com.catcher.javanium.ledger;

import java.util.List;

import com.catcher.javanium.blockchain.transaction.Output;
import com.catcher.javanium.blockchain.transaction.UnspentTransaction;

public interface Ledger {

	/** Add transaction to the ledger. */
	public void addUnspentTransaction(UnspentTransaction unspentTransaction, Output output);
	/** Remove transaction from the ledger. */
	public void removeUnspentTransaction(UnspentTransaction unspentTransaction);
	/** Get transaction output from the ledger. */
	public Output getTransactionOutput(UnspentTransaction unspentTransaction);
	/** Get a list of all unspent transactions */
	public List<UnspentTransaction> getAllUnspentTransactions();
	/** Deep copy of the ledger */
	public Ledger copy(Ledger ledger);

	public boolean contains(UnspentTransaction unspentTransaction);
	public boolean contains(Output transactionOutput);

}
