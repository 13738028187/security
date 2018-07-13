package com.hzdy.entity;

public class SecurityAreaConfig {

	private Integer configId;
	private String networkNumber;
	private Integer securityAreaId;

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public String getNetworkNumber() {
		return networkNumber;
	}

	public void setNetworkNumber(String networkNumber) {
		this.networkNumber = networkNumber;
	}

	public Integer getSecurityAreaId() {
		return securityAreaId;
	}

	public void setSecurityAreaId(Integer securityAreaId) {
		this.securityAreaId = securityAreaId;
	}

	@Override
	public String toString() {
		return "SecurityAreaConfig [configId=" + configId + ", networkNumber=" + networkNumber + ", securityAreaId="
				+ securityAreaId + "]";
	}

}
