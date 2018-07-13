package com.hzdy.entity;

public class WhitelistRuleS7 {

	private Integer whitelistRuleS7Id;
	private String ruleName;
	private String originIP;
	private String goalIP;
	private String originIPmac;
	private String goalIPmac;
	private String readwriteAttr;
	private String registerArea;
	private String dbAreaNum;
	private String pointType;
	private String initialAddr;
	private String endAddr;
	private String protocol;

	public Integer getWhitelistRuleS7Id() {
		return whitelistRuleS7Id;
	}

	public void setWhitelistRuleS7Id(Integer whitelistRuleS7Id) {
		this.whitelistRuleS7Id = whitelistRuleS7Id;
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

	public String getReadwriteAttr() {
		return readwriteAttr;
	}

	public void setReadwriteAttr(String readwriteAttr) {
		this.readwriteAttr = readwriteAttr;
	}

	public String getRegisterArea() {
		return registerArea;
	}

	public void setRegisterArea(String registerArea) {
		this.registerArea = registerArea;
	}

	public String getDbAreaNum() {
		return dbAreaNum;
	}

	public void setDbAreaNum(String dbAreaNum) {
		this.dbAreaNum = dbAreaNum;
	}

	public String getPointType() {
		return pointType;
	}

	public void setPointType(String pointType) {
		this.pointType = pointType;
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
		return "WhitelistRuleS7 [whitelistRuleS7Id=" + whitelistRuleS7Id + ", ruleName=" + ruleName + ", originIP="
				+ originIP + ", goalIP=" + goalIP + ", originIPmac=" + originIPmac + ", goalIPmac=" + goalIPmac
				+ ", readwriteAttr=" + readwriteAttr + ", registerArea=" + registerArea + ", dbAreaNum=" + dbAreaNum
				+ ", pointType=" + pointType + ", initialAddr=" + initialAddr + ", endAddr=" + endAddr + ", protocol="
				+ protocol + "]";
	}

}
