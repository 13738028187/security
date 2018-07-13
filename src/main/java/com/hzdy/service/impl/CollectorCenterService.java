package com.hzdy.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hzdy.hardware.entity.SystemActualInfo;
import com.hzdy.hardware.entity.SystemInfo;


@Service("collectorCenterService")
public class CollectorCenterService {
	
	@Resource
	private JavaInformationsService javaInformationsService;
	
	@Resource
	private SystemActualInfoService systemActualInfoService;
	@Resource
	private SystemInfoServiceImpl systemInfoService;

	public int save(SystemInfo systemInfo) {
		return systemInfoService.save(systemInfo);
	}

	public SystemInfo querySystemInfo() {
		return systemInfoService.queryAll();
	}
	

	public List<String> queryJavaInformations() {
		return javaInformationsService.queryAll();
	}

	public List<String> querySystemActualInfo() {
		List<String> result=new ArrayList<>();
		systemActualInfoService.queryAll().stream().forEach(s->{
			result.add(JSONObject.toJSON(s).toString());
		});
		return result;
	}
	public SystemActualInfo queryUpToDate(String collectionName) {
		return systemActualInfoService.queryUpToDate(collectionName);
	}
}
