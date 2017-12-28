package com.catcher.javanium.blockchain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;

import com.catcher.javanium.blockchain.block.merkletree.BasicMerkleTree;
import com.catcher.javanium.blockchain.transaction.Transaction;

public class BasicMerkleTreeTest {

	@Test
	public void verifyRealBlockchainHash()  {

		Transaction trans1 = new Transaction();
		Transaction trans2 = new Transaction();
		Transaction trans3 = new Transaction();

		Assert.assertTrue(Hex.encodeHexString(trans1.hash()).equals("cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e"));
		Assert.assertTrue(Hex.encodeHexString(trans2.hash()).equals(Hex.encodeHexString(trans1.hash())));
		Assert.assertTrue(Hex.encodeHexString(trans3.hash()).equals(Hex.encodeHexString(trans3.hash())));

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(trans1);
		transactions.add(trans2);
		transactions.add(trans3);

		Assert.assertEquals("f4eafa285f02709bbd228cedc53e01b2bdcb61af63859a4b92f2413756898c848a347a447594b676e76d45ce38a6da251f69b13e790cc3b2049d3b09c54bfa64", Hex.encodeHexString(new BasicMerkleTree().getRoot(transactions.toArray(new Transaction[0]))));
	}

}
