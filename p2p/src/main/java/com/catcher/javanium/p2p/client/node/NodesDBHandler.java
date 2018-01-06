package com.catcher.javanium.p2p.client.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.logging.Logger;

import com.catcher.javanium.p2p.database.DatabaseHandler;
import com.zaxxer.hikari.HikariDataSource;

public class NodesDBHandler {

	private static HikariDataSource datasource = DatabaseHandler.createNewDatabase("P2PClient");
	private static Logger log = Logger.getLogger(NodesDBHandler.class.getName());
	static {
		createTable();
	}


	/**
	 *Insert P2P node to the database. (if exists)
	 * @param ip
	 */
	public static void insertNode(P2PNode node) throws SQLException{
		try(Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement("INSERT OR REPLACE INTO NODE (IP, LAST_SEEN) VALUES (?, ?)")) {

			statement.setString(1, node.getIp());
			statement.setLong(2, node.getLastSeen().getTime());

			statement.executeUpdate();
		}
	}

	/**
	 * Gets a P2P node from the database. (if exists)
	 * @param ip
	 */
	public static Optional<P2PNode> getNode(String ip){
		try(Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT IP, LAST_SEEN FROM NODE WHERE IP = ? limit 1")) {
			statement.setString(1, ip);
			try (ResultSet rs = statement.executeQuery()){
				if (rs.next()){
					String nodeIP = rs.getString(1);
					Timestamp lastSeen = rs.getTimestamp(2);
					return Optional.of(new P2PNode(nodeIP, lastSeen));
				}
			}
		} catch (SQLException e){
			log.severe("Failed to insert node.");
		}
		return Optional.empty();
	}

	/**
	 * Remove a P2P node from the database. (if exists)
	 * @param ip
	 */
	public static void removeNode(String ip){
		try(Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM NODE WHERE IP = ?")) {
			statement.setString(1, ip);
			statement.executeUpdate();
		} catch (SQLException e){
			log.severe("Failed to insert node.");
		}
	}

	private static Connection getConnection() throws SQLException {
		return datasource.getConnection();
	}

	private static void createTable(){
		try(Connection connection = getConnection();
				Statement statement = connection.createStatement()) {
			statement.execute(""
					+ "CREATE TABLE IF NOT EXISTS NODE ("
					+ "IP TEXT NOT NULL PRIMARY KEY,"
					+ "LAST_SEEN INTEGER NOT NULL)");
		} catch (SQLException e){
			log.severe("Failed to create table");
		}
	}


}
