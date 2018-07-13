package com.hzdy.dao;

import com.hzdy.hardware.entity.SystemInfo;

public interface SystemInfoDao {
	SystemInfo queryAll();

	int save(SystemInfo systemInfo);
}
