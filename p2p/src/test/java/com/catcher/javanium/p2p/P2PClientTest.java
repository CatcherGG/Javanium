package com.catcher.javanium.p2p;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import org.junit.Assert;
import org.junit.Test;

import com.catcher.javanium.p2p.client.node.NodesDBHandler;
import com.catcher.javanium.p2p.client.node.P2PNode;

public class P2PClientTest {

	@Test
	public void nodeHandling() throws SQLException{
		String ip = "127.0.0.1";
		Timestamp lastSeen = Timestamp.from(Instant.now()); 

		P2PNode node = new P2PNode(ip, lastSeen);
		NodesDBHandler.insertNode(node);

		P2PNode actualNode = NodesDBHandler.getNode(ip).orElseThrow(()-> new IllegalStateException("Node not found"));

		Assert.assertEquals(actualNode, node);

		NodesDBHandler.removeNode(ip);

		Assert.assertFalse(NodesDBHandler.getNode(ip).isPresent());
	}

}
