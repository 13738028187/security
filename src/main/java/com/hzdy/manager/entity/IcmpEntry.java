package com.hzdy.manager.entity;

import java.util.Map;

/**
* 
* what
* 
* @author kirohuji
* @version 0.1
*/
public class IcmpEntry {
	private String oid="1.3.6.1.2.1.5";
	private String description;
	private int number=26;
	private Map<String, String> map;
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
	public void setMap(Map<String, String> map2) {
		this.map = map2;
	}
	@Override
	public String toString() {
		return "IcmpEntry [oid=" + oid + ", description=" + description + ", number=" + number + ", map=" + map + "]";
	}
	
}
