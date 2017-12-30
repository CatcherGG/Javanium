package com.catcher.javanium.p2p.server;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.catcher.javanium.p2p.server.subscribers.SubscriberHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Path("subscribe")
public class SubscriberServlet {

	Gson gson = new Gson();

	@GET
	@Produces("application/json")
	public String getSubscribers() {
		return gson.toJson(SubscriberHandler.getSubscribers());
	}

	@POST
	@Produces("application/json")
	public String subscribe(@Context HttpServletRequest request) {
		SubscriberHandler.insertSubscriber(request.getRemoteAddr());

		JsonObject reponse = new JsonObject();
		reponse.addProperty("message", "Successfully inserted subscriber");
		return gson.toJson(reponse);
	}

}
