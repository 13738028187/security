package com.hzdy.dao;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.SecurityArea;
import com.hzdy.entity.SecurityAreaDevice;

/**
 * 安全域管理
 * @author zhangjun
 * @date 2017.10.31
 */
public interface SecurityAreaDao {
	
	/**
	 * 查询安全域
	 * @param map
	 * @return
	 * @date 2017.10.31
	 */
	List<SecurityArea> queryList(Map<String, Object> map);
	
	/**
	 * 根据ID查询安全域
	 * @param id
	 * @return
	 * @date 2017.10.31
	 */
	SecurityArea querySecurityAreaById(Integer id);
	
	/**
	 * 查询总数
	 * @param map
	 * @return
	 * @date 2017.10.31
	 */
	int queryTotal(Map<String, Object> map);
	
	int insert(SecurityArea securityArea);
	
	int update(SecurityArea securityArea);
	
	int delete(int id);
	
	/**
	 * 根据安全域ID查询包含的设备接口
	 * @param map
	 * @return
	 */
	List<SecurityAreaDevice> queryDevices(Map<String, Object> map);
	
	/**
	 * 查询设备记录总数
	 * @param map
	 * @return
	 */
	Integer queryDeviceTotal(Map<String, Object> map);
	
	/**
	 * 根据ID查询设备接口信息
	 * @param map
	 * @return
	 */
	List<SecurityAreaDevice> queryDeviceInterfaceById(Map<String, Object> map);
	
	/**
	 * 检查当前安全域是否有重复
	 * @param device
	 * @return
	 */
	SecurityAreaDevice checkDuplication1(SecurityAreaDevice device);
	
	/**
	 * 检查其他安全域是否有重复
	 * @param device
	 * @return
	 */
	SecurityAreaDevice checkDuplication2(SecurityAreaDevice device);
	
	/**
	 * 更新安全域设备接口信息
	 * @param device
	 */
	void updateDeviceInterface(SecurityAreaDevice device);
	
	/**
	 * 添加安全域设备接口信息
	 * @param device
	 */
	void insertDeviceInterface(SecurityAreaDevice device);
	
	/**
	 * 根据设备ID删除安全域设备接口信息
	 * @param deviceId
	 */
	void deleteDeviceInterfaceByDeviceId(Integer deviceId);
	
	/**
	 * 根据安全域ID删除安全域设备接口信息
	 * @param securityAreaId
	 */
	void deleteDeviceInterfaceBySecurityAreaId(Integer securityAreaId);
	
	/**
	 * 根据安全域ID查询等级
	 * @param securityAreaId
	 * @return
	 */
	String queryLevelById(Integer securityAreaId);
	
}
