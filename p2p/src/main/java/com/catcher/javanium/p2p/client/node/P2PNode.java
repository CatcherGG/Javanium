package com.catcher.javanium.p2p.client.node;

import java.sql.SQLException;
import java.sql.Timestamp;

public class P2PNode {

	String ip;
	Timestamp lastSeen;

	public P2PNode(String ip, Timestamp lastSeen) {
		this.ip = ip;
		this.lastSeen = lastSeen;
	}

	public void save() throws SQLException{
		NodesDBHandler.insertNode(this);
	}

	public final String getIp() {
		return ip;
	}

	public final Timestamp getLastSeen() {
		return lastSeen;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((lastSeen == null) ? 0 : lastSeen.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		P2PNode other = (P2PNode) obj;
		if (ip == null) {
			if (other.ip != null) {
				return false;
			}
		} else if (!ip.equals(other.ip)) {
			return false;
		}
		if (lastSeen == null) {
			if (other.lastSeen != null) {
				return false;
			}
		} else if (!lastSeen.equals(other.lastSeen)) {
			return false;
		}
		return true;
	}



}
