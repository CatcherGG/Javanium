package com.catcher.javanium.blockchain;

import java.io.IOException;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.IndexTreeList;
import org.mapdb.Serializer;

import com.catcher.javanium.blockchain.block.Block;
import com.catcher.javanium.crypto.digitalsignature.RSASignature;



public class BlockChainDatabaseTest {


	@Test
	public void DBTest(){

		DB db = DBMaker.fileDB("blockchaintest.db").closeOnJvmShutdown().make();

		IndexTreeList<Block> blockchain = db.indexTreeList("blockchaintest", new Serializer<Block>() {

			@Override
			public Block deserialize(DataInput2 input, int arg1) throws IOException {
				byte[] data = new byte[arg1];
				input.readFully(data);
				Block block = SerializationUtils.deserialize(data);
				return block;
			}

			@Override
			public void serialize(DataOutput2 output, Block block) throws IOException {
				output.write(SerializationUtils.serialize(block));
			}
		}).createOrOpen();


		RSASignature sign = new RSASignature();
		Block block = new Block(new byte[0], sign.generateKeys().getPublic(), 1);
		blockchain.add(block);

		Assert.assertEquals(block.hashCode(), blockchain.get(0).hashCode());
	}
}
