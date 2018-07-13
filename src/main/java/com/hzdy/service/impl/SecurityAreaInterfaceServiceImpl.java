package com.hzdy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzdy.dao.SecurityAreaDao;
import com.hzdy.dao.SecurityAreaDeviceDao;
import com.hzdy.dao.SecurityAreaInterfaceDao;
import com.hzdy.entity.SecurityArea;
import com.hzdy.entity.SecurityAreaDevice;
import com.hzdy.entity.SecurityAreaInterface;
import com.hzdy.service.SecurityAreaDeviceService;
import com.hzdy.service.SecurityAreaInterfaceService;
import com.hzdy.service.SecurityAreaService;

@Service("securityAreaInterfaceService")
public class SecurityAreaInterfaceServiceImpl implements SecurityAreaInterfaceService {
	@Resource
	private SecurityAreaInterfaceDao securityAreaInterfaceDao;

	@Override
	public List<SecurityAreaInterface> queryList(Map<String, Object> map) {
		return securityAreaInterfaceDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return securityAreaInterfaceDao.queryTotal(map);
	}

	@Override
	public int save(SecurityAreaInterface securityAreaInterface) {
		return securityAreaInterfaceDao.save(securityAreaInterface);
	}

	@Override
	public int update(SecurityAreaInterface securityAreaInterface) {
		return securityAreaInterfaceDao.update(securityAreaInterface);
	}

	@Override
	public int delete(int id) {
		return securityAreaInterfaceDao.delete(id);
	}

	@Override
	public List<String> getInterfaces(Integer deviceId) {
		// TODO Auto-generated method stub
		return securityAreaInterfaceDao.getInterfaces(deviceId);
	}

	@Override
	public void updateInterface(Map<String, Object> map) {
		// TODO Auto-generated method stub
		securityAreaInterfaceDao.updateInterface(map);
	}

	@Override
	public void updateInterfaceById(Integer interfaceId) {
		// TODO Auto-generated method stub
		securityAreaInterfaceDao.updateInterfaceById(interfaceId);
	}

	@Override
	public void updateInterfaceBySecurityAreaId(Integer securityAreaId) {
		// TODO Auto-generated method stub
		securityAreaInterfaceDao.updateInterfaceBySecurityAreaId(securityAreaId);
	}

	
}
