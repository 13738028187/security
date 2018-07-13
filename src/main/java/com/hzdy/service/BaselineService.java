package com.hzdy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hzdy.entity.BaselineDevice;
import com.hzdy.entity.BaselineStrategy;

/**
 * 基线配置业务类
 * @author yuwenming
 * @date 2017.11.16
 */
@Service("baselineService")
public interface BaselineService {

	/**
	 * 查询基线设备
	 * @return
	 */
	List<BaselineDevice> queryDevices();
	
	/**
	 * 根据id查询基线设备
	 * @param deviceId
	 * @return
	 */
	BaselineDevice queryDeviceById(Integer deviceId);
	
	/**
	 * 查询基线配置策略
	 * @param name
	 * @return
	 */
	List<BaselineStrategy> queryStrategies(String name);
	
	/**
	 * 根据ID查询策略信息
	 * @param strategyId
	 * @return
	 */
	BaselineStrategy queryStrategyById(Integer strategyId);
	
	/**
	 * 插入基线配置策略
	 * @param strategy
	 * @return
	 */
	String insertStrategy(BaselineStrategy strategy);
	
	/**
	 * 修改基线配置策略
	 * @param strategy
	 * @return
	 */
	String updateStrategy(BaselineStrategy strategy);
	
	/**
	 * 删除基线配置策略
	 * @param strategyId
	 * @return
	 */
	String deleteStrategy(String[] strategyIds);
	
	/**
	 * 策略下发
	 * @param strategyId
	 * @param deviceIds
	 * @return
	 */
	String strategyIssued(Integer strategyId, String[] deviceIds);
	
}
