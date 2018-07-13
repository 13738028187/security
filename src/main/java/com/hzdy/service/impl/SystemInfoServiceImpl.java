package com.hzdy.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hzdy.dao.PriorityDao;
import com.hzdy.dao.SystemInfoDao;
import com.hzdy.hardware.entity.SystemInfo;
import com.hzdy.service.SystemInfoService;
@Service("systemInfoService")
public class SystemInfoServiceImpl implements SystemInfoService{
	@Resource
	private SystemInfoDao systemInfoDao;
	
	@Override
	public SystemInfo queryAll() {
		return systemInfoDao.queryAll();
	}

	@Override
	public int save(SystemInfo systemInfo) {
		try{
			return systemInfoDao.save(systemInfo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
