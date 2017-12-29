package com.catcher.javanium.blockchain.unspenttransaction;

import java.util.List;

import com.catcher.javanium.blockchain.transaction.Output;
import com.catcher.javanium.blockchain.transaction.UnspentTransaction;

public interface UnspentTransactionsPool{

	/** Add transaction to the ledger. */
	public void addUnspentTransaction(UnspentTransaction unspentTransaction, Output output);
	/** Remove transaction from the ledger. */
	public void removeUnspentTransaction(UnspentTransaction unspentTransaction);
	/** Get transaction output from the ledger. */
	public Output getTransactionOutput(UnspentTransaction unspentTransaction);
	/** Get a list of all unspent transactions */
	public List<UnspentTransaction> getAllUnspentTransactions();
	/** Deep copy of the ledger */
	public UnspentTransactionsPool copy(UnspentTransactionsPool ledger);

	public boolean contains(UnspentTransaction unspentTransaction);
	public boolean contains(Output transactionOutput);

}
