package com.hzdy.service;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.SecurityArea;
import com.hzdy.entity.SecurityAreaDevice;

/**
 * 安全域类
 * @author zhangjun
 * @date 2017.10.31
 */
public interface SecurityAreaService {

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
	
	Integer queryDeviceTotal(Map<String, Object> map);
	
	/**
	 * 更新安全域设备接口信息
	 * @param device
	 * @return
	 */
	String updateDeviceInterface(SecurityAreaDevice device);
	
	/**
	 * 添加安全域设备接口信息
	 * @param device
	 * @return
	 */
	String insertDeviceInterface(SecurityAreaDevice device);
	
	/**
	 * 删除安全域设备接口信息
	 * @param deviceIds
	 * @return
	 */
	String deleteDeviceInterface(String[] deviceIds);
	
}
