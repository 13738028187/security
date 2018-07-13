package com.hzdy.hardware;

import javax.annotation.Resource;

import com.hzdy.hardware.entity.SystemActualInfo;
import com.hzdy.hardware.entity.SystemActualInfoFactory;
import com.hzdy.mongo.dao.MongoManager;
import com.hzdy.mongo.dao.SystemActualInfoDao;
import com.hzdy.service.impl.SystemActualInfoService;

public class RemoteCaller {
/*	@Resource
	private SystemActualInfoService systemActualInfoService;
	*/
	private static RemoteCaller remoteCaller;

	public static RemoteCaller getInstance() {
		if (remoteCaller == null) {
			return new RemoteCaller();
		}
		return remoteCaller;
	}

	public void collector() {
		try {
			SystemActualInfo systemActualInfo = new SystemActualInfo();
			SystemActualInfoFactory.getInstance().build(systemActualInfo);
			MongoManager.getInstance().insert("system_actual_info", systemActualInfo);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
