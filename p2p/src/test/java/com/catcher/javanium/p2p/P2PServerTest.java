package com.catcher.javanium.p2p;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.catcher.javanium.p2p.server.P2PServer;
import com.catcher.javanium.p2p.utilities.Contains;
import com.catcher.javanium.p2p.utilities.EqualsTo;
import com.jayway.restassured.RestAssured;

public class P2PServerTest {

	static P2PServer p2pserver;

	@BeforeClass
	public static void startServer(){
		p2pserver = new P2PServer(3448);
		p2pserver.startServer();
	}


	@Test
	public void testSubscribe(){
		RestAssured.expect()
		.statusCode(200)
		.and()
		.body("message", new EqualsTo("Successfully inserted subscriber"))
		.when()
		.post("http://127.0.0.1:3448/subscribe");
	}

	@Test
	public void testGetSubscribers(){

		RestAssured.post("http://127.0.0.1:3448/subscribe"); // Make sure you are registered.

		RestAssured.expect()
		.statusCode(200)
		.and()
		.body(new Contains("127.0.0.1"))
		.when()
		.get("http://127.0.0.1:3448/subscribe");
	}


	@AfterClass
	public static void stopServer(){
		p2pserver.stopServer();
	}


}
