package com.hzdy.service;

import java.util.ArrayList;

import com.hzdy.discovery.Device;
import com.hzdy.discovery.Ip;
import com.hzdy.manager.entity.DeviceActualResources;
import com.hzdy.manager.entity.DeviceStaticResources;

/**
* 
* what
* 
* @author kirohuji
* @version 0.1
*/
public interface IpService {
	int save(Ip t);//插入数据
	
	int update(Ip t);//更新数据
	
	int delete(int id);//根据主键ID 删除 
	
	int deleteBatch(Object[] id);//根据条件批量删除 

	Ip queryObject(Object id);//根据条件查询单条记录
	
	int queryTotal();//获取总记录数
	
	ArrayList<Ip> queryAll();

	void saveAllIp(ArrayList<String> ipList);

	DeviceActualResources getDeviceActualResources(String ipAddress, String community);

	DeviceStaticResources getDeviceStaticResources(String ipAddress, String community);
	
    void clear();
	
	
}
