package com.hzdy.discovery;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Host implements Serializable{
	private String ipAddress;
	private String MACAddress;
	private String subNet;
	private int Type;
	
	public Host() {
		super();
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getMACAddress() {
		return MACAddress;
	}
	public void setMACAddress(String mACAddress) {
		MACAddress = mACAddress;
	}

	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
	
	public String getSubNet() {
		return subNet;
	}
	public void setSubNet(String subNet) {
		this.subNet = subNet;
	}
	public Host(String ipAddress, String mACAddress, int type) {
		super();
		this.ipAddress = ipAddress;
		MACAddress = mACAddress;
		Type = type;
	}
	@Override
	public String toString() {
		return "Host [ipAddress=" + ipAddress + ", MACAddress=" + MACAddress + ", subNet=" + subNet + ", Type=" + Type
				+ "]";
	}
	
}
