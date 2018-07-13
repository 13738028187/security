package com.hzdy.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hzdy.entity.SecurityAreaConfig;

/**
 * 安全域配置业务类
 * @author yuwenming
 *
 */
@Service("securityAreaConfigService")
public interface SecurityAreaConfigService {
	
	/**
	 * 查询安全域配置信息
	 * 
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
	String insertSecurityAreaConfig(SecurityAreaConfig config);

	/**
	 * 删除安全域配置信息
	 * 
	 * @param configId
	 */
	String deleteSecurityAreaConfig(String[] configIds);

	/**
	 * 更新安全域配置信息
	 * 
	 * @param config
	 */
	String updateSecurityAreaConfig(SecurityAreaConfig config);
	
	/**
	 * 根据安全域ID删除配置信息
	 * @param securityAreaId
	 */
	void deleteConfigBySecurityAreaId(Integer securityAreaId);

}
