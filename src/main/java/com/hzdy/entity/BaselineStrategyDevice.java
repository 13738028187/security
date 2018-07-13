package com.hzdy.entity;

public class BaselineStrategyDevice {

	private Integer strategyId;
	private Integer deviceId;

	public Integer getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(Integer strategyId) {
		this.strategyId = strategyId;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public String toString() {
		return "BaselineStrategyDevice [strategyId=" + strategyId + ", deviceId=" + deviceId + "]";
	}

}
