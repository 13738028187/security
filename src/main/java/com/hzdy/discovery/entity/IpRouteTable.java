package com.hzdy.discovery.entity;

import java.util.ArrayList;

/**
 * @author Administrator
 * 
 * This entity's IP Routing table
 */
public class IpRouteTable {
	private ArrayList<IpRouteEntry> ipRouteEntry;

	public ArrayList<IpRouteEntry> getIpRouteEntry() {
		return ipRouteEntry;
	}

	public void setIpRouteEntry(ArrayList<IpRouteEntry> ipRouteEntry) {
		this.ipRouteEntry = ipRouteEntry;
	}

	@Override
	public String toString() {
		return "IpRouteTable [ipRouteEntry=" + ipRouteEntry + "]";
	}
	
}
