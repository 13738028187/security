package com.hzdy.service;

import java.util.List;

import com.hzdy.entity.Interfaces;

public interface InterfacesService {
	Interfaces queryById(int id);
	
	List<Interfaces> queryBySecurityAreaId(int securityAreaId);
	
	int insert(Interfaces interfaces);
	
	int update(Interfaces interfaces);
	
	int delete(int id);
	
	int deleteBySecurityAreaId(int securityAreaId);
}
