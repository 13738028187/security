package com.hzdy.entity;

public class KeyEvents {
	private int id;
	private String ruleName;
	private String originIp;
	private String goalIp;
	private String originIpMask;
	private String goalIpMask;

	public KeyEvents() {
		super();
	}

	public int getId() {
		return id;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOriginIp() {
		return originIp;
	}

	public void setOriginIp(String originIp) {
		this.originIp = originIp;
	}

	public String getGoalIp() {
		return goalIp;
	}

	public void setGoalIp(String goalIp) {
		this.goalIp = goalIp;
	}

	public String getOriginIpMask() {
		return originIpMask;
	}

	public void setOriginIpMask(String originIpMask) {
		this.originIpMask = originIpMask;
	}

	public String getGoalIpMask() {
		return goalIpMask;
	}

	public void setGoalIpMask(String goalIpMask) {
		this.goalIpMask = goalIpMask;
	}

	@Override
	public String toString() {
		return "KeyEvents [id=" + id + ", ruleName=" + ruleName + ", originIp=" + originIp + ", goalIp=" + goalIp
				+ ", originIpMask=" + originIpMask + ", goalIpMask=" + goalIpMask + "]";
	}

}
