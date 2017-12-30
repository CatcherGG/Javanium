package com.catcher.javanium.p2p.server.subscribers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBHandler {

	private static HikariDataSource datasource = createNewDatabase();
	private static Logger log = Logger.getLogger(DBHandler.class.getName());

	public static void initialize(){
		createTable();
	}

	public static Connection getConnection() throws SQLException {
		return datasource.getConnection();
	}

	private static HikariDataSource createNewDatabase() {

		Properties props = new Properties();
		props.setProperty("dataSourceClassName", "org.sqlite.SQLiteDataSource");
		props.setProperty("dataSource.databaseName", "P2PServer");

		HikariConfig config = new HikariConfig(props);

		return new HikariDataSource(config);
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
