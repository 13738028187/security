package com.hzdy.mongo.dao;

import java.util.List;

import com.hzdy.discovery.entity.json.NetworkGraph;

/**
* 
* what
* 
* @author kirohuji
* @version 0.1
*/
public interface NetworkGraphDao  extends MongoBase<NetworkGraph>{
	public <T> void push(String collectionName, String key, T value);

	public List<NetworkGraph> findAll(String collectionName);
}
