package com.hzdy.entity;

public class BaselineStrategy {

	private Integer strategyId;
	private String name;
	private Integer type; // 0表示端口策略，1表示IP策略
	private String ips;
	private Integer ipStatus;
	private String ports;
	private Integer portStatus;

	public Integer getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(Integer sttrategyId) {
		this.strategyId = sttrategyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIps() {
		return ips;
	}

	public void setIps(String ips) {
		this.ips = ips;
	}

	public Integer getIpStatus() {
		return ipStatus;
	}

	public void setIpStatus(Integer ipStatus) {
		this.ipStatus = ipStatus;
	}

	public String getPorts() {
		return ports;
	}

	public void setPorts(String ports) {
		this.ports = ports;
	}

	public Integer getPortStatus() {
		return portStatus;
	}

	public void setPortStatus(Integer portStatus) {
		this.portStatus = portStatus;
	}

	@Override
	public String toString() {
		return "BaselineStrategy [strategyId=" + strategyId + ", name=" + name + ", type=" + type + ", ips=" + ips
				+ ", ipStatus=" + ipStatus + ", ports=" + ports + ", portStatus=" + portStatus + "]";
	}

}
