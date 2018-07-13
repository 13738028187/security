package com.hzdy.entity;

public class UserDefinedOPC {

	private Integer userDefinedOPCId;
	private String ruleName;
	private String originIP;
	private String goalIP;
	private String protocol;
	private String interfaces;
	private String method;

	public Integer getUserDefinedOPCId() {
		return userDefinedOPCId;
	}

	public void setUserDefinedOPCId(Integer userDefinedOPCId) {
		this.userDefinedOPCId = userDefinedOPCId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getOriginIP() {
		return originIP;
	}

	public void setOriginIP(String originIP) {
		this.originIP = originIP;
	}

	public String getGoalIP() {
		return goalIP;
	}

	public void setGoalIP(String goalIP) {
		this.goalIP = goalIP;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(String interfaces) {
		this.interfaces = interfaces;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "UserDefinedOPC [userDefinedOPCId=" + userDefinedOPCId + ", ruleName=" + ruleName + ", originIP="
				+ originIP + ", goalIP=" + goalIP + ", protocol=" + protocol + ", interfaces=" + interfaces
				+ ", method=" + method + "]";
	}

}
