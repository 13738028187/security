package com.hzdy.dao;

import java.util.List;

import com.hzdy.logger.entity.WebLogger;
;
public interface WebLoggerDao  extends BaseDao<WebLogger> {
	List<WebLogger> queryAll();

	int saveObject(WebLogger webLogger);
}
