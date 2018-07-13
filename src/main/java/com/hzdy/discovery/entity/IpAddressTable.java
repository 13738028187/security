package com.hzdy.discovery.entity;

import java.util.ArrayList;

/**
 * @author Administrator
 * 
 * The table of addressing information relevant to this  entity's IP address
 */
public class IpAddressTable {
	private ArrayList<IpAddrEntry> ipAddrEntry;

	public ArrayList<IpAddrEntry> getIpAddrEntry() {
		return ipAddrEntry;
	}

	public void setIpAddrEntry(ArrayList<IpAddrEntry> ipAddrEntry) {
		this.ipAddrEntry = ipAddrEntry;
	}

	@Override
	public String toString() {
		return "IpAddressTable [ipAddrEntry=" + ipAddrEntry + "]";
	}
	
}
