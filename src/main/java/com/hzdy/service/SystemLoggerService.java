package com.hzdy.service;

import java.util.List;

import com.hzdy.logger.entity.SystemLogger;

public interface SystemLoggerService {
	List<SystemLogger> queryAll();

	int saveObject(SystemLogger systemLogger);
}
