package com.hzdy.service;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.SecurityArea;
import com.hzdy.entity.SecurityAreaDevice;
import com.hzdy.entity.SecurityAreaInterface;

/**
 * 安全域类
 * @author zhangjun
 * @date 2017.10.31
 */
public interface SecurityAreaInterfaceService {
	
	List<SecurityAreaInterface> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	int save(SecurityAreaInterface securityAreaInterface);
	
	int update(SecurityAreaInterface securityAreaInterface);
	
	int delete(int id);
	
	List<String> getInterfaces(Integer deviceId);
	
	void updateInterface(Map<String, Object> map);
	
	void updateInterfaceById(Integer interfaceId);
	
	void updateInterfaceBySecurityAreaId(Integer securityAreaId);
	
}
