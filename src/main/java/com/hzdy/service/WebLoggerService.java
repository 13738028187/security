package com.hzdy.service;

import java.util.List;

import com.hzdy.logger.entity.WebLogger;


public interface WebLoggerService {
	 List<WebLogger> queryAll();
	 int saveObject( WebLogger webLogger);
}
