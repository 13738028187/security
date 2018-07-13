package com.hzdy.manager.entity;

import java.util.List;
import java.util.Map;

/**
 * 
 * what
 * 
 * @author kirohuji
 * @version 0.1
 */
public class UdpEntry {
	private String oid = "1.3.6.1.2.1.7";
	private String description;
	private int number = 5;
	private Map<String, String> map;
    private UdpTable udpTable;
    
	public UdpTable getUdpTable() {
		return udpTable;
	}

	public void setUdpTable(UdpTable udpTable) {
		this.udpTable = udpTable;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public class UdpTable {
		private String oid = "1.3.6.1.2.1.7.5.1";
		private String description;
		private int number = 2;
		private Map<String, List<String>> map;

		public String getOid() {
			return oid;
		}

		public void setOid(String oid) {
			this.oid = oid;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public int getNumber() {
			return number;
		}

		public void setNumber(int number) {
			this.number = number;
		}

		public Map<String, List<String>> getMap() {
			return map;
		}

		public void setMap(Map<String, List<String>> udpEntry) {
			this.map = udpEntry;
		}

		@Override
		public String toString() {
			return "UdpTable [oid=" + oid + ", description=" + description + ", number=" + number + ", map=" + map
					+ "]";
		}

	}
}
