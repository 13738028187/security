package com.hzdy.dao;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.SecurityArea;
import com.hzdy.entity.SecurityAreaDevice;
import com.hzdy.logger.entity.Email;

/**
 * 安全域管理
 * @author zhangjun
 * @date 2017.10.31
 */
public interface SecurityAreaDeviceDao extends BaseDao<SecurityAreaDevice>{
	public SecurityAreaDevice getInterfaces(int deviceId);
	public SecurityAreaDevice querySecurityAreaDeviceById(int deviceId);
	public SecurityAreaDevice getInterfaces(int device_id, int securityAreaId);

}
