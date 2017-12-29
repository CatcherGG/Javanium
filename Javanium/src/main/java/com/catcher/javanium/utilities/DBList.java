package com.catcher.javanium.utilities;

import java.io.IOException;

import org.apache.commons.lang3.SerializationUtils;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import com.catcher.javanium.blockchain.block.Block;

public class DBList {

	private static final DB db = DBMaker.fileDB("blockchain.db").closeOnJvmShutdown().make();
	private static volatile HTreeMap<byte[], Block> dbInstance;


	public static HTreeMap<byte[], Block> getInstance(){
		if (dbInstance == null){
			synchronized (DBList.class) {
				HTreeMap<byte[], Block> instance = dbInstance;
				if (instance == null) {
					instance = dbInstance = db.hashMap("blockchain", 
							new Serializer<byte[]>() {
								@Override
								public byte[] deserialize(DataInput2 input, int arg1) throws IOException {
									byte[] data = new byte[arg1];
									input.readFully(data);
									return SerializationUtils.deserialize(data);
								}

								@Override
								public void serialize(DataOutput2 output, byte[] block) throws IOException {
									output.write(SerializationUtils.serialize(block));
								}
							},	new Serializer<Block>() {					
								@Override
								public Block deserialize(DataInput2 input, int arg1) throws IOException {
									byte[] data = new byte[arg1];
									input.readFully(data);
									return SerializationUtils.deserialize(data);
								}

								@Override
								public void serialize(DataOutput2 output, Block block) throws IOException {
									output.write(SerializationUtils.serialize(block));
								}
							}


							).createOrOpen();
					return instance;
				}
			}
		}
		return dbInstance;
	}



}
