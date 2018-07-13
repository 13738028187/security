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
public interface SecurityAreaDeviceService {
	
	List<SecurityAreaDevice> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	int save(SecurityAreaDevice securityAreaDevice);
	
	int update(SecurityAreaDevice securityAreaDevice);
	
	int delete(int id);
	
    SecurityAreaDevice getInterfaces(int device_id);
    
    SecurityAreaDevice querySecurityAreaDeviceById(int deviceId);
}
