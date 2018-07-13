package com.hzdy.dao;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.SecurityAreaInterface;

/**
 * 安全域管理
 * 
 * @author zhangjun
 * @date 2017.10.31
 */
public interface SecurityAreaInterfaceDao extends BaseDao<SecurityAreaInterface> {

	List<String> getInterfaces(Integer deviceId);
	
	void updateInterface(Map<String, Object> map);
	
	void updateInterfaceById(Integer interfaceId);
	
	void updateInterfaceBySecurityAreaId(Integer securityAreaId);

}
