package com.hzdy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hzdy.hardware.entity.SystemActualInfo;
import com.hzdy.mongo.dao.SystemActualInfoDao;

@Service("systemActualInfoService")
public class SystemActualInfoService {
	@Resource
	private SystemActualInfoDao systemActualInfoDaoImpl;
	
	public List<SystemActualInfo> queryAll() {
		Map<String, Object> params = new HashMap<String, Object>();
		return systemActualInfoDaoImpl.findAll(params, "system_actual_info");
	}
	
	public void insert(SystemActualInfo s,String collectionName) {
		systemActualInfoDaoImpl.insert(s, collectionName);
	}
	public SystemActualInfo queryUpToDate(String collectionName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("", "");
		String query="name";
		return systemActualInfoDaoImpl.findOne(params, collectionName, query);
	}
}
