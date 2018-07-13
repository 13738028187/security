package com.hzdy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hzdy.dao.WebLoggerDao;
import com.hzdy.logger.entity.WebLogger;
import com.hzdy.service.WebLoggerService;
@Service("webLoggerService")
public class WebLoggerServiceImpl implements WebLoggerService{
	@Resource
	private WebLoggerDao webLoggerDao;
	@Override
	public List<WebLogger> queryAll() {
		return webLoggerDao.queryAll();
	}
	@Override
	public int saveObject(WebLogger webLogger) {
		System.out.println("连接数据库");
		int i=0;
				
		try {
			i=webLoggerDao.saveObject(webLogger);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return i;
	}
}
