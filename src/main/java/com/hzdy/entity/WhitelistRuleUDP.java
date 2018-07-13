package com.hzdy.entity;

public class WhitelistRuleUDP {

	private Integer whitelistRuleUDPId;
	private String ruleName;
	private String originIP;
	private String goalIP;
	private String originIPmac;
	private String goalIPmac;

	public Integer getWhitelistRuleUDPId() {
		return whitelistRuleUDPId;
	}

	public void setWhitelistRuleUDPId(Integer whitelistRuleUDPId) {
		this.whitelistRuleUDPId = whitelistRuleUDPId;
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

	@Override
	public String toString() {
		return "WhitelistRuleUDP [whitelistRuleUDPId=" + whitelistRuleUDPId + ", ruleName=" + ruleName + ", originIP="
				+ originIP + ", goalIP=" + goalIP + ", originIPmac=" + originIPmac + ", goalIPmac=" + goalIPmac + "]";
	}

}
