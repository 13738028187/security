package com.hzdy.entity;

public class WhitelistRuleMODBUS {

	private Integer whitelistRuleMODBUSId;
	private String ruleName;
	private String originIP;
	private String goalIP;
	private String originIPmac;
	private String goalIPmac;
	private String functionCode;
	private String initialAddr;
	private String endAddr;
	private String protocol;

	public Integer getWhitelistRuleMODBUSId() {
		return whitelistRuleMODBUSId;
	}

	public void setWhitelistRuleMODBUSId(Integer whitelistRuleMODBUSId) {
		this.whitelistRuleMODBUSId = whitelistRuleMODBUSId;
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

	public String getOriginIPmac() {
		return originIPmac;
	}

	public void setOriginIPmac(String originIPmac) {
		this.originIPmac = originIPmac;
	}

	public String getGoalIPmac() {
		return goalIPmac;
	}

	public void setGoalIPmac(String goalIPmac) {
		this.goalIPmac = goalIPmac;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public String getInitialAddr() {
		return initialAddr;
	}

	public void setInitialAddr(String initialAddr) {
		this.initialAddr = initialAddr;
	}

	public String getEndAddr() {
		return endAddr;
	}

	public void setEndAddr(String endAddr) {
		this.endAddr = endAddr;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	@Override
	public String toString() {
		return "WhitelistRuleMODBUS [whitelistRuleMODBUSId=" + whitelistRuleMODBUSId + ", ruleName=" + ruleName + ", originIP="
				+ originIP + ", goalIP=" + goalIP + ", originIPmac=" + originIPmac + ", goalIPmac=" + goalIPmac
				+ ", functionCode=" + functionCode + ", initialAddr=" + initialAddr + ", endAddr=" + endAddr
				+ ", protocol=" + protocol + "]";
	}

}
