package com.hzdy.discovery.entity;

import java.util.ArrayList;

/**
 * @author Administrator
 *
 * The IP Address Translation table used for mapping from IP address to physical addresses.
 */
public class IpNetToMediaTable {
	private ArrayList<IpNetToMediaEntry> ipNetToMediaEntry;

	public ArrayList<IpNetToMediaEntry> getIpNetToMediaEntry() {
		return ipNetToMediaEntry;
	}

	public void setIpNetToMediaEntry(ArrayList<IpNetToMediaEntry> ipNetToMediaEntry) {
		this.ipNetToMediaEntry = ipNetToMediaEntry;
	}

	@Override
	public String toString() {
		return "IpNetToMediaTable [ipNetToMediaEntry=" + ipNetToMediaEntry + "]";
	}
	
}
