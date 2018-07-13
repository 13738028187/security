package com.hzdy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzdy.dao.SecurityAreaDao;
import com.hzdy.dao.SecurityAreaDeviceDao;
import com.hzdy.entity.SecurityArea;
import com.hzdy.entity.SecurityAreaDevice;
import com.hzdy.service.SecurityAreaDeviceService;
import com.hzdy.service.SecurityAreaService;

@Service("securityAreaDeviceService")
public class SecurityAreaDeviceServiceImpl implements SecurityAreaDeviceService {
	@Resource
	private SecurityAreaDeviceDao securityAreaDeviceDao;

	@Override
	public List<SecurityAreaDevice> queryList(Map<String, Object> map) {
		return securityAreaDeviceDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return securityAreaDeviceDao.queryTotal(map);
	}

	@Override
	public int save(SecurityAreaDevice securityAreaDevice) {
		return securityAreaDeviceDao.save(securityAreaDevice);
	}

	@Override
	public int update(SecurityAreaDevice securityAreaDevice) {
		return securityAreaDeviceDao.update(securityAreaDevice);
	}

	@Override
	public int delete(int id) {
		return securityAreaDeviceDao.delete(id);
	}

	@Override
	public SecurityAreaDevice getInterfaces(int device_id) {
		return securityAreaDeviceDao.getInterfaces(device_id);
	}

	@Override
	public SecurityAreaDevice querySecurityAreaDeviceById(int deviceId) {
		return securityAreaDeviceDao.querySecurityAreaDeviceById(deviceId);
	}

	
}
