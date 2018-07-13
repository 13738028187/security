package com.hzdy.service;

import com.hzdy.hardware.entity.SystemInfo;

public interface SystemInfoService {
	 SystemInfo queryAll();
	 int save(SystemInfo systemInfo);
}
