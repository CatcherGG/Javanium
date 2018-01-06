package com.catcher.javanium.p2p.server;

import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.catcher.javanium.p2p.server.subscribers.Cleaner;
import com.catcher.javanium.p2p.server.subscribers.SubscriberDBHandler;

public class P2PServer {

	Logger log = Logger.getLogger(P2PServer.class.toString());
	Server server;
	int port;

	public P2PServer() {
		this(8443);
	}

	public P2PServer(int port) {
		this.port = port;
	}

	public void startServer() {
		try {
			SubscriberDBHandler.initialize();
			Cleaner.runJob();

			ResourceConfig resourceConfig = new ResourceConfig();		
			resourceConfig.packages(SubscriberServlet.class.getPackage().getName());
			ServletContainer servletContainer = new ServletContainer(resourceConfig);
			ServletHolder servletHolder = new ServletHolder(servletContainer);     

			server = new Server(port);
			ServletContextHandler context = new ServletContextHandler();
			context.setContextPath("/");
			context.addServlet(servletHolder, "/*");

			server.setHandler(context);

			server.start();
		} catch (Exception e){
			log.throwing(this.getClass().getName(), "startServer", e);
		}
	}

	public void stopServer() {
		try {
			server.stop();
		} catch (Exception e) {
			log.throwing(this.getClass().getName(), "stopServer", e);
		}
	}

}
