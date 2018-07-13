package com.hzdy.discovery.entity;

public class IpNetToMediaEntry {
	private String ipNetToMediaIfIndex;
	private String ipNetToMediaPhysAddress;
	private String ipNetToMediaNetAddress;
	private String ipNetToMediaType;
	public String getIpNetToMediaIfIndex() {
		return ipNetToMediaIfIndex;
	}
	public void setIpNetToMediaIfIndex(String ipNetToMediaIfIndex) {
		this.ipNetToMediaIfIndex = ipNetToMediaIfIndex;
	}
	public String getIpNetToMediaPhysAddress() {
		return ipNetToMediaPhysAddress;
	}
	public void setIpNetToMediaPhysAddress(String ipNetToMediaPhysAddress) {
		this.ipNetToMediaPhysAddress = ipNetToMediaPhysAddress;
	}
	public String getIpNetToMediaNetAddress() {
		return ipNetToMediaNetAddress;
	}
	public void setIpNetToMediaNetAddress(String ipNetToMediaNetAddress) {
		this.ipNetToMediaNetAddress = ipNetToMediaNetAddress;
	}
	public String getIpNetToMediaType() {
		return ipNetToMediaType;
	}
	public void setIpNetToMediaType(String ipNetToMediaType) {
		this.ipNetToMediaType = ipNetToMediaType;
	}
	@Override
	public String toString() {
		return "IpNetToMediaEntry [ipNetToMediaIfIndex=" + ipNetToMediaIfIndex + ", ipNetToMediaPhysAddress="
				+ ipNetToMediaPhysAddress + ", ipNetToMediaNetAddress=" + ipNetToMediaNetAddress + ", ipNetToMediaType="
				+ ipNetToMediaType + "]";
	}
	
}
