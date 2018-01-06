package com.catcher.javanium.p2p.database;

import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseHandler {

	public static HikariDataSource createNewDatabase(String name) {

		Properties props = new Properties();
		props.setProperty("dataSourceClassName", "org.sqlite.SQLiteDataSource");
		props.setProperty("dataSource.databaseName", name);

		HikariConfig config = new HikariConfig(props);

		return new HikariDataSource(config);
	}

}
