package com.catcher.javanium.p2p;

import org.junit.Test;

import com.catcher.javanium.p2p.utilities.EqualsTo;
import com.jayway.restassured.RestAssured;

public class P2PServerTest {

	@Test
	public void testGetSubscribers(){
		RestAssured.expect()
		.statusCode(200)
		.and()
		.body("message", new EqualsTo("Successfully inserted subscriber"))
		.when()
		.post("http://127.0.0.1:8443/subscribe");
	}


}
