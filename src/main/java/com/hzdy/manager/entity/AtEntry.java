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
public class AtEntry {
	private String oid="1.3.6.1.2.1.3";
	private String description;
	private int number=1;
	private AtTable atTable;
	
	public AtTable getAtTable() {
		return atTable;
	}

	public void setAtTable(AtTable atTable) {
		this.atTable = atTable;
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
	public class AtTable{
		private String oid="1.3.6.1.2.1.3.1.1";
		private String description;
		private int number=3;
		private Map<String,List<String>> map;
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
		public void setMap(Map<String, List<String>> map) {
			this.map = map;
		}
		@Override
		public String toString() {
			return "AtTable [oid=" + oid + ", description=" + description + ", number=" + number + ", map=" + map + "]";
		}
		
		
	}
}
