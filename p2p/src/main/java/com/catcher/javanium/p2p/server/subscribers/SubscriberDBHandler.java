package com.catcher.javanium.p2p.server.subscribers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import com.catcher.javanium.p2p.database.DatabaseHandler;
import com.zaxxer.hikari.HikariDataSource;

public class SubscriberDBHandler {

	private static HikariDataSource datasource = DatabaseHandler.createNewDatabase("P2PServer");
	private static Logger log = Logger.getLogger(SubscriberDBHandler.class.getName());


	public static void initialize(){
		createTable();
	}

	public static Connection getConnection() throws SQLException {
		return datasource.getConnection();
	}

	private static void createTable(){
		try(Connection connection = datasource.getConnection();
				Statement statement = connection.createStatement()) {
			statement.execute(""
					+ "CREATE TABLE IF NOT EXISTS SUBSCRIBERS ("
					+ "IP TEXT NOT NULL PRIMARY KEY,"
					+ "LAST_SEEN INTEGER NOT NULL)");
		} catch (SQLException e){
			log.severe("Failed to create table");
		}
	}


}
