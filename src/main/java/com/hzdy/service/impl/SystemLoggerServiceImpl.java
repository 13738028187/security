package com.hzdy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hzdy.dao.SystemLoggerDao;
import com.hzdy.logger.entity.SystemLogger;
import com.hzdy.service.SystemLoggerService;
@Service("systemLoggerService")
public class SystemLoggerServiceImpl implements SystemLoggerService{
	@Resource
	private SystemLoggerDao systemLoggerDao;
	
	@Override
	public List<SystemLogger> queryAll() {
		return systemLoggerDao.queryAll();
	}

	@Override
	public int saveObject(SystemLogger systemLogger) {
		// TODO Auto-generated method stub
		return systemLoggerDao.saveObject(systemLogger);
	}
	
}
