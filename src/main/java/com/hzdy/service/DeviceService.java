package com.hzdy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hzdy.discovery.Device;

/**
* 
* what
* 
* @author kirohuji
* @version 0.1
*/
public interface DeviceService {
	int save(Device t);//插入数据
	
	int update(Device t);//更新数据
	
	int delete(int id);//根据主键ID 删除 
	
	int deleteBatch(Object[] id);//根据条件批量删除 

	Device queryObject(Object id);//根据条件查询单条记录
	
	int queryTotal();//获取总记录数
	
	ArrayList<Device> queryAll();
}
