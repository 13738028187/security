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
public class TcpEntry {
	private String oid="1.3.6.1.2.1.6";
	private String description;
	private int number=15;
	private Map<String,String> map;
	private TcpTable tcpTable;
	
	public class TcpTable{
		private String oid="1.3.6.1.2.1.6.13.1";
		private String description;
		private int number=5;
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
		public void setMap(Map<String, List<String>> tcpEntry) {
			this.map = tcpEntry;
		}
		
	}
	public TcpTable getTcpTable() {
		return tcpTable;
	}
	public void setTcpTable(TcpTable tcpTable) {
		this.tcpTable = tcpTable;
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
	
	
}
