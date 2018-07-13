package com.hzdy.discovery.entity;

import java.util.List;

import org.snmp4j.smi.IpAddress;

/**
 * @author Administrator
 * 
 * 
 */
public class Router {
	private IpAddressTable ipAddressTable;
	private IpNetToMediaTable ipNetToMediaTable;
	private IpRouteTable ipRouteTable;
	public IpAddressTable getIpAddressTable() {
		return ipAddressTable;
	}
	public void setIpAddressTable(IpAddressTable ipAddressTable) {
		this.ipAddressTable = ipAddressTable;
	}
	public IpNetToMediaTable getIpNetToMediaTable() {
		return ipNetToMediaTable;
	}
	public void setIpNetToMediaTable(IpNetToMediaTable ipNetToMediaTable) {
		this.ipNetToMediaTable = ipNetToMediaTable;
	}
	public IpRouteTable getIpRouteTable() {
		return ipRouteTable;
	}
	public void setIpRouteTable(IpRouteTable ipRouteTable) {
		this.ipRouteTable = ipRouteTable;
	}
	@Override
	public String toString() {
		return "Router [ipAddressTable=" + ipAddressTable + ", ipNetToMediaTable=" + ipNetToMediaTable
				+ ", ipRouteTable=" + ipRouteTable + "]";
	}
	
}
