package com.hzdy.entity;

public class BaselineDevice {

	private Integer deviceId;
	private String deviceName;
	private String ip;
	private String deviceUsername;
	private String devicePassword;
	private String type;

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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDeviceUsername() {
		return deviceUsername;
	}

	public void setDeviceUsername(String deviceUsername) {
		this.deviceUsername = deviceUsername;
	}

	public String getDevicePassword() {
		return devicePassword;
	}

	public void setDevicePassword(String devicePassword) {
		this.devicePassword = devicePassword;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "BaselineDevice [deviceId=" + deviceId + ", deviceName=" + deviceName + ", ip=" + ip
				+ ", deviceUsername=" + deviceUsername + ", devicePassword=" + devicePassword + ", type=" + type + "]";
	}

}
