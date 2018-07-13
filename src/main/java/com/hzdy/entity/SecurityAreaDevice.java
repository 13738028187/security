package com.hzdy.entity;

import java.util.List;

public class SecurityAreaDevice {
	private Integer deviceId;
	private String deviceName;
	private String deviceNumber;
	private String deviceIP;
    private List<SecurityAreaInterface> interfaces;
	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getDeviceIP() {
		return deviceIP;
	}

	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}

	public List<SecurityAreaInterface> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<SecurityAreaInterface> interfaces) {
		this.interfaces = interfaces;
	}
	
}
