package com.catcher.javanium.p2p.server.subscribers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SubscriberHandler {

	private static Logger log = Logger.getLogger(SubscriberHandler.class.getName());

	/**
	 * This inserts subscriber IP to the server database.
	 * @param Subscriber IP.
	 */
	public static void insertSubscriber(String ip){

		try(Connection connection = DBHandler.getConnection();
				PreparedStatement statement = connection.prepareStatement("INSERT OR REPLACE INTO SUBSCRIBERS (IP, LAST_SEEN) VALUES (?, ?)")) {
			statement.setString(1, ip);
			statement.setLong(2, System.currentTimeMillis());
			statement.execute();
		} catch (SQLException e){
			log.severe("Failed to create table");
		}
	}


	/**
	 * This will get the list of subscribers to the p2p network.
	 * @return List of the last seen IPS.
	 */
	public static List<String> getSubscribers(){

		List<String> ips = new ArrayList<>();

		try(Connection connection = DBHandler.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT IP FROM SUBSCRIBERS LIMIT 100")) {

			while (resultSet.next()){
				ips.add(resultSet.getString(1));
			}

		} catch (SQLException e){
			log.severe("Failed to create table");
		}

		return ips;
	}

}
