package com.hzdy.dao;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.SecurityAreaConfig;

/**
 * 安全域配置
 * @author yuwenming
 *
 */
public interface SecurityAreaConfigDao {
	
	/**
	 * 查询安全域配置信息
	 * @param map
	 * @return
	 */
	List<SecurityAreaConfig> querySecurityAreaConfig(Map<String, Object> map);

	/**
	 * 查询安全域配置信息总数
	 * 
	 * @param map
	 * @return
	 */
	Integer queryTotal(Map<String, Object> map);

	/**
	 * 检查重复
	 * 
	 * @param config
	 * @return
	 */
	SecurityAreaConfig checkDuplication(SecurityAreaConfig config);

	/**
	 * 根据ID查询安全域配置信息
	 * 
	 * @param configId
	 * @return
	 */
	SecurityAreaConfig querySecurityConfigById(Integer configId);

	/**
	 * 添加安全域配置信息
	 * 
	 * @param config
	 */
	void insertSecurityAreaConfig(SecurityAreaConfig config);

	/**
	 * 删除安全域配置信息
	 * 
	 * @param configId
	 */
	void deleteSecurityAreaConfig(Integer configId);

	/**
	 * 更新安全域配置信息
	 * 
	 * @param config
	 */
	void updateSecurityAreaConfig(SecurityAreaConfig config);
	
	/**
	 * 根据安全域ID删除配置信息
	 * @param securityAreaId
	 */
	void deleteConfigBySecurityAreaId(Integer securityAreaId);
	
	/**
	 * 根据网络号查询安全域ID
	 * @param networkNumber
	 * @return
	 */
	Integer querySecurityAreaIdByNum(String networkNumber);
	
}
