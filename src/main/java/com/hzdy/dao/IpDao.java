package com.hzdy.dao;

import java.util.ArrayList;

import com.hzdy.discovery.Ip;

/**
* 
* 设备发现后的保存
* 
* @author kirohuji
* @version 0.1
*/
public interface IpDao extends BaseDao<Ip> {
	public ArrayList<Ip> queryAll();
	
	public void clear();
}
