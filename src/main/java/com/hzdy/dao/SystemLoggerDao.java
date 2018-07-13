package com.hzdy.dao;

import java.util.List;

import com.hzdy.logger.entity.SystemLogger;

public interface SystemLoggerDao {
	List<SystemLogger> queryAll();

	int saveObject(SystemLogger systemLogger);
}
