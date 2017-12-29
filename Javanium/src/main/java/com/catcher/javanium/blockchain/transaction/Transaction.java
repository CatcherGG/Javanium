package com.catcher.javanium.blockchain.transaction;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;


public class Transaction implements Serializable{

	private static final long serialVersionUID = -3091411374641985629L;

	/** hash of the transaction, its unique id */
	protected byte[] hash;
	protected transient List<Input> inputs;
	protected transient List<Output> outputs;


	public Transaction() {
		inputs = new ArrayList<>();
		outputs = new ArrayList<>();
	}

	public Transaction(Transaction transaction) {
		hash = transaction.hash.clone();
		inputs = new ArrayList<>(transaction.inputs);
		outputs = new ArrayList<>(transaction.outputs);
	}

	public void addInput(byte[] prevTransactionHash, int outputIndex) {
		Input in = new Input(prevTransactionHash, outputIndex);
		inputs.add(in);
	}

	public void addOutput(double value, PublicKey address) {
		Output op = new Output(value, address);
		outputs.add(op);
	}

	public void removeInput(int index) {
		inputs.remove(index);
	}

	public void removeInput(UnspentTransaction ut) {
		for (int i = 0; i < inputs.size(); i++) {
			Input in = inputs.get(i);
			UnspentTransaction u = new UnspentTransaction(in.outputTransactionHash, in.outputIndex);
			if (u.equals(ut)) {
				inputs.remove(i);
				return;
			}
		}
	}

	public byte[] getTransactionData(int index) {

		Input in = inputs.get(index);

		byte[] prevTranscationHash = in.outputTransactionHash;
		byte[] signature = in.signature;
		byte[] outputIndexBytes = Ints.toByteArray(in.outputIndex);
		byte[] transactionData = Bytes.concat(prevTranscationHash, signature, outputIndexBytes);
		for (Output op : outputs) {
			transactionData = Bytes.concat(transactionData, op.toBytes());
		}

		return transactionData;
	}

	public void addSignature(byte[] signature, int index) {
		inputs.get(index).addSignature(signature);
	}

	public byte[] hash() {
		HashFunction hashFunction = Hashing.sha512();

		HashCode hashCode = hashFunction.newHasher()
				.putObject(inputs, (inputsList, into) -> 
				inputsList.forEach(input -> into.putBytes(input.toBytes())))
				.putObject(outputs, (outputsList, into) ->
				outputsList.forEach(output -> into.putBytes(output.toBytes())))
				.hash();

		hash = hashCode.asBytes();
		return hash;
	}

	public List<Input> getInputs() {
		return inputs;
	}

	public List<Output> getOutputs() {
		return outputs;
	}

	public Input getInput(int index) {
		if (index < inputs.size()) {
			return inputs.get(index);
		}
		return null;
	}

	public Output getOutput(int index) {
		if (index < outputs.size()) {
			return outputs.get(index);
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(hash);
		result = prime * result + ((inputs == null) ? 0 : inputs.hashCode());
		result = prime * result + ((outputs == null) ? 0 : outputs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Transaction other = (Transaction) obj;
		if (!Arrays.equals(hash, other.hash)) {
			return false;
		}
		if (inputs == null) {
			if (other.inputs != null) {
				return false;
			}
		} else if (!inputs.equals(other.inputs)) {
			return false;
		}
		if (outputs == null) {
			if (other.outputs != null) {
				return false;
			}
		} else if (!outputs.equals(other.outputs)) {
			return false;
		}
		return true;
	}

	public boolean isCoinbase(){
		return false;
	}

}
