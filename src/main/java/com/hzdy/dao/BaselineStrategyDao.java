package com.hzdy.dao;

import java.util.List;

import com.hzdy.entity.BaselineStrategy;

/**
 * 基线配置（策略）
 * @author yuwenming
 *
 */
public interface BaselineStrategyDao {

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
	 * 检查是否有重复
	 * @param strategyId
	 * @return
	 */
	BaselineStrategy checkDuplication(BaselineStrategy strategy);
	
	/**
	 * 插入基线配置策略
	 * @param strategy
	 */
	void insertStrategy(BaselineStrategy strategy);
	
	/**
	 * 修改基线配置策略
	 * @param strategy
	 */
	void updateStrategy(BaselineStrategy strategy);
	
	/**
	 * 删除基线配置策略
	 * @param strategyId
	 */
	void deleteStrategy(Integer strategyId);
	
}
