package com.hzdy.mongo.dao;

import com.hzdy.discovery.entity.json.FixNetworkGraph;

/**
* 
* what
* 
* @author kirohuji
* @version 0.1
*/
public interface FixNetworkGraphDao  extends MongoBase<FixNetworkGraph>{
	public <T> void push(String collectionName, String key, T value);
}
