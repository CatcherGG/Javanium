package com.catcher.javanium.p2p.server;

import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.catcher.javanium.p2p.server.subscribers.DBHandler;

public class P2PServer {

	Logger log = Logger.getLogger(P2PServer.class.toString());

	public void startServer() {
		try {
			ResourceConfig resourceConfig = new ResourceConfig();		
			resourceConfig.packages(SubscriberServlet.class.getPackage().getName());
			ServletContainer servletContainer = new ServletContainer(resourceConfig);
			ServletHolder servletHolder = new ServletHolder(servletContainer);     

			Server server = new Server(8443);
			ServletContextHandler context = new ServletContextHandler();
			context.setContextPath("/");
			context.addServlet(servletHolder, "/*");

			server.setHandler(context);

			server.start();
			server.join();
		} catch (Exception e){
			log.throwing(this.getClass().getName(), "configServer", e);
		}
	}

	public static void main(String[] args){
		DBHandler.initialize();
		new P2PServer().startServer();
	}

}
