package com.hzdy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hzdy.discovery.entity.json.FixNetworkGraph;
import com.hzdy.discovery.entity.json.Link;
import com.hzdy.discovery.entity.json.NetworkGraph;
import com.hzdy.discovery.entity.json.Node;
import com.hzdy.logger.entity.Syslog;
import com.hzdy.manager.entity.DeviceActualResources;
import com.hzdy.manager.entity.DeviceStaticResources;
import com.hzdy.mongo.dao.DeviceActualResourcesDao;
import com.hzdy.mongo.dao.DeviceStaticResourcesDao;
import com.hzdy.mongo.dao.FixNetworkGraphDao;
import com.hzdy.mongo.dao.NetworkGraphDao;
import com.hzdy.mongo.dao.SyslogDao;

/**
 * 
 * 注意这个类服务是基于Mongodb的 这个类是用来封装日志的增删改查
 * 
 * @author kirohuji
 * @version 0.1
 */
@Service
public class SyslogService{
	@Resource
	private SyslogDao syslogDao;

	
	public void insert(Syslog object, String collectionName) {
		syslogDao.insert(object, collectionName);
		
	}

	
	public Syslog findOne(Map<String, Object> params, String collectionName, String query) {
		// TODO Auto-generated method stub
		return syslogDao.findOne(params, collectionName, query);
	}

	
	public List<Syslog> findList(Map<String, Object> params, String collectionName) {
		// TODO Auto-generated method stub
		return syslogDao.findList(params, collectionName);
	}

	
	public List<Syslog> findAll(Map<String, Object> params, String collectionName) {
		// TODO Auto-generated method stub
		return syslogDao.findAll(params, collectionName);
	}

	
	public Syslog findOne(Map<String, Object> params, String collectionName, String query, String projection) {
		// TODO Auto-generated method stub
		return syslogDao.findOne(params, collectionName, query);
	}

	
	public Syslog findList(Map<String, Object> params, String collectionName, String query, String projection) {
		// TODO Auto-generated method stub
		return syslogDao.findList(params, collectionName, query, projection);
	}

	
	public List<Syslog> findAll(Map<String, Object> params, String collectionName, String projection) {
		// TODO Auto-generated method stub
		return syslogDao.findAll(params, collectionName);
	}

	
	public void update(Map<String, Object> params, String collectionName, String query, String value) {
		syslogDao.update(params, collectionName, query, value);
		
	}

	
	public void createCollection(String collectionName) {
		syslogDao.createCollection(collectionName);
		
	}

	
	public void remove(Map<String, Object> params, String collectionName) {
		syslogDao.remove(params, collectionName);
		
	}

}
