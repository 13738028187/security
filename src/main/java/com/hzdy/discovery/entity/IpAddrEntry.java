package com.hzdy.discovery.entity;

/**
 * @author Administrator
 * 
 * 
 */
public class IpAddrEntry {
	private String ipAdEntAddr;
	private String ipAdEntIfIndex;
	private String ipAdEntNetMask;
	private String ipAdentBcastAddr;
	private String ipAdEntReasmMaxSize;
	public String getIpAdEntAddr() {
		return ipAdEntAddr;
	}
	public void setIpAdEntAddr(String ipAdEntAddr) {
		this.ipAdEntAddr = ipAdEntAddr;
	}
	public String getIpAdEntIfIndex() {
		return ipAdEntIfIndex;
	}
	public void setIpAdEntIfIndex(String ipAdEntIfIndex) {
		this.ipAdEntIfIndex = ipAdEntIfIndex;
	}
	public String getIpAdEntNetMask() {
		return ipAdEntNetMask;
	}
	public void setIpAdEntNetMask(String ipAdEntNetMask) {
		this.ipAdEntNetMask = ipAdEntNetMask;
	}
	public String getIpAdEntReasmMaxSize() {
		return ipAdEntReasmMaxSize;
	}
	public void setIpAdEntReasmMaxSize(String ipAdEntReasmMaxSize) {
		this.ipAdEntReasmMaxSize = ipAdEntReasmMaxSize;
	}
	public String getIpAdentBcastAddr() {
		return ipAdentBcastAddr;
	}
	public void setIpAdentBcastAddr(String ipAdentBcastAddr) {
		this.ipAdentBcastAddr = ipAdentBcastAddr;
	}
	@Override
	public String toString() {
		return "IpAddrEntry [ipAdEntAddr=" + ipAdEntAddr + ", ipAdEntIfIndex=" + ipAdEntIfIndex + ", ipAdEntNetMask="
				+ ipAdEntNetMask + ", ipAdentBcastAddr=" + ipAdentBcastAddr + ", ipAdEntReasmMaxSize="
				+ ipAdEntReasmMaxSize + "]";
	}
	
	
}
