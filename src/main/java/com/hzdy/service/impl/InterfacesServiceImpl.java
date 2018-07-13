package com.hzdy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hzdy.dao.InterfacesDao;
import com.hzdy.entity.Interfaces;
import com.hzdy.service.InterfacesService;

@Service("interfacesService")
public class InterfacesServiceImpl implements InterfacesService {
	@Resource
	private InterfacesDao interfacesDao;
	
	@Override
	public Interfaces queryById(int id) {
		// TODO Auto-generated method stub
		return interfacesDao.queryById(id);
	}

	@Override
	public List<Interfaces> queryBySecurityAreaId(int securityAreaId) {
		// TODO Auto-generated method stub
		return interfacesDao.queryBySecurityAreaId(securityAreaId);
	}

	@Override
	public int insert(Interfaces interfaces) {
		// TODO Auto-generated method stub
		return interfacesDao.insert(interfaces);
	}

	@Override
	public int update(Interfaces interfaces) {
		// TODO Auto-generated method stub
		return interfacesDao.update(interfaces);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return interfacesDao.delete(id);
	}

	@Override
	public int deleteBySecurityAreaId(int securityAreaId) {
		// TODO Auto-generated method stub
		return interfacesDao.deleteBySecurityAreaId(securityAreaId);
	}
}
