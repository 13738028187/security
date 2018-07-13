package com.hzdy.dao;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.BaselineDevice;

/**
 * 基线配置（设备）
 * @author yuwenming
 */
public interface BaselineDeviceDao {

	/**
	 * 查询基线管理设备
	 * @return
	 */
	List<BaselineDevice> queryDevices();
	
	/**
	 * 根据id查找基线设备
	 * @param deviceId
	 * @return
	 */
	BaselineDevice queryDeviceById(Integer deviceId);
	
	/**
	 * 更新密码
	 * @param deviceId
	 * @param newPassword
	 * @return
	 */
	Integer updatePassword(Map<String, Object> map);
	
	/**
	 * 根据设备IP查询设备信息
	 * @param deviceIP
	 * @return
	 */
	BaselineDevice queryDeviceByIP(String deviceIP);
	
}
