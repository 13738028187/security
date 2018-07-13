package com.hzdy.entity;

public class WebCount {
	private int id;
	private String ruleName;
	private String originIp;
	private String goalIp;
	private String originIpMask;
	private String goalIpMask;
	private String protocol;
	private String startOriginPort;
	private String endOriginPort;
	private String startGoalPort;
	private String endGoalPort;

	public WebCount() {
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

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getStartOriginPort() {
		return startOriginPort;
	}

	public void setStartOriginPort(String startOriginPort) {
		this.startOriginPort = startOriginPort;
	}

	public String getEndOriginPort() {
		return endOriginPort;
	}

	public void setEndOriginPort(String endOriginPort) {
		this.endOriginPort = endOriginPort;
	}

	public String getStartGoalPort() {
		return startGoalPort;
	}

	public void setStartGoalPort(String startGoalPort) {
		this.startGoalPort = startGoalPort;
	}

	public String getEndGoalPort() {
		return endGoalPort;
	}

	public void setEndGoalPort(String endGoalPort) {
		this.endGoalPort = endGoalPort;
	}

	@Override
	public String toString() {
		return "WebCount [id=" + id + ", ruleName=" + ruleName + ", originIp=" + originIp + ", goalIp=" + goalIp
				+ ", originIpMask=" + originIpMask + ", goalIpMask=" + goalIpMask + ", protocol=" + protocol
				+ ", startOriginPort=" + startOriginPort + ", endOriginPort=" + endOriginPort + ", startGoalPort="
				+ startGoalPort + ", endGoalPort=" + endGoalPort + "]";
	}

}
