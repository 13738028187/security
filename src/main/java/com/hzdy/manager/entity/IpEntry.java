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
public class IpEntry {
	private String oid="1.3.6.1.2.1.4";
	private String description;
	private int number=23;
	private Map<String,String> map;
	private IpAddrTable ipAddrTable;
	private IpRouteTable ipRouteTable;
	private IpNetToMediaTable ipNetToMediaTable;
	
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
	public IpAddrTable getIpAddrTable() {
		return ipAddrTable;
	}
	public void setIpAddrTable(IpAddrTable ipAddrTable) {
		this.ipAddrTable = ipAddrTable;
	}
	public IpRouteTable getIpRouteTable() {
		return ipRouteTable;
	}
	public void setIpRouteTable(IpRouteTable ipRouteTable) {
		this.ipRouteTable = ipRouteTable;
	}
	public IpNetToMediaTable getIpNetToMediaTable() {
		return ipNetToMediaTable;
	}
	public void setIpNetToMediaTable(IpNetToMediaTable ipNetToMediaTable) {
		this.ipNetToMediaTable = ipNetToMediaTable;
	}
	public class IpAddrTable{
		private String oid="1.3.6.1.2.1.4.20.1";
		private String description;
		private int number=5;
		private Map<String,String> map;
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
	public class IpRouteTable{
		private String oid="1.3.6.1.2.1.4.21.1";
		private String description;
		private int number=13;
		private Map<String,String> map;
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
	public class IpNetToMediaTable{
		private String oid="1.3.6.1.2.1.4.22.1";
		private String description;
		private int number=4;
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
		
	}
}
