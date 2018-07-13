package com.hzdy.dao;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.BaselineStrategyDevice;

public interface BaselineStrategyDeviceDao {
	
	/**
	 * 根据策略ID查询设备策略信息
	 * @param strategyId
	 * @return
	 */
	List<BaselineStrategyDevice> queryStrategyDevice(Integer strategyId);

	/**
	 * 插入设备策略信息
	 * @param strategyId
	 * @param deviceId
	 */
	void insertStrategyDevice(Integer strategyId, Integer deviceId);
	
	/**
	 * 删除设备策略信息
	 * @param strategyId
	 * @param deviceId
	 */
	void deleteStrategyDevice(Integer strategyId, Integer deviceId);
	
}
