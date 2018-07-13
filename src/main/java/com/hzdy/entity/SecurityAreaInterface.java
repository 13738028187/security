package com.hzdy.entity;

public class SecurityAreaInterface {
	private Integer interfaceId;
	private String interfaceName;
	private Integer deviceId;
	private Integer securityareaId;
	private SecurityAreaDevice device;

	public SecurityAreaDevice getDevice() {
		return device;
	}

	public void setDevice(SecurityAreaDevice device) {
		this.device = device;
	}

	public Integer getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(Integer interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getSecurityareaId() {
		return securityareaId;
	}

	public void setSecurityareaId(Integer securityareaId) {
		this.securityareaId = securityareaId;
	}

}
