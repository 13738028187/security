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
public class InterfacesEntry {
	private String oid="1.3.6.1.2.1.2";
	private String description;
	private int number=2;
	private Map<String, String> map;
	private IfTable ifTable;
	public class IfTable{
		private String oid="1.3.6.1.2.1.2.2.1";
		private String description;
		private int number=22;
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
		public void setMap(Map<String, List<String>> ifEntry) {
			this.map = ifEntry;
		}
		@Override
		public String toString() {
			return "IfTable [oid=" + oid + ", description=" + description + ", number=" + number + ", map=" + map + "]";
		}
		
		
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

	public void setMap(Map<String, String> ifNumber) {
		this.map = ifNumber;
	}

	public IfTable getIfTable() {
		return ifTable;
	}

	public void setIfTable(IfTable ifTable) {
		this.ifTable = ifTable;
	}

	@Override
	public String toString() {
		return "InterfacesEntry [oid=" + oid + ", description=" + description + ", number=" + number + ", map=" + map
				+ ", ifTable=" + ifTable + "]";
	}
	
}
