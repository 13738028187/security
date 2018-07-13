package com.hzdy.mongo.dao;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hzdy.hardware.TransportFormat;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

/**
 * 这个类主要是用来封装mongodb的操作
 * 
 * @author kirohuji
 *
 */
public class MongoManager {
	/*
	 * private Mongo m=null; private DB database = null;
	 */
	private Mongo mongo;

	// 打开数据库test
	private DB db;
	private static MongoManager mongoManager;
	private int port = 27017;

	public static MongoManager getInstance() {
		if (mongoManager == null) {
			return mongoManager = new MongoManager();
		} else {
			return mongoManager;
		}
	}

	public MongoManager() {
		try {
			mongo = new Mongo("192.168.2.9", port);
			db = mongo.getDB("db_shiro");
		} catch (Exception e) {
			System.out.println("无法连接数据库");
		}

	}
	public void connection(String database) {
		db = mongo.getDB("db_shiro");
	}

	public void clear(String collectionName) {
		DBCollection dbCol = db.getCollection(collectionName);
		dbCol.drop();
	}

	public List<String> queryAll(String collectionName) {
		ArrayList<String> lists = new ArrayList<String>();
		DBCollection dbCol = db.getCollection(collectionName);
	      DBCursor dbCursor = dbCol.find();
	        for (DBObject dbObject : dbCursor) {
	        	lists.add((String) JSONObject.toJSON(dbObject));
	        }
		/*
		 * MongoCursor<SystemActualInfo> all = dbCol.find().as(SystemActualInfo.class);
		 */
		/*
		 * FindIterable ret = dbCol.find();
		 * 
		 * while (ret.hasNext()) { BasicDBObject bdbObj = (BasicDBObject) ret.next(); if
		 * (bdbObj != null) { lists.add(bdbObj.toString()); } } ret.close();
		 * 
		 * for (Object o : ret) { BasicDBObject bdbObj = o; if (o != null) {
		 * lists.add(o.toString()); } }
		 */
		return lists;
	}
	public void insert(String collectionName, Serializable s) {
		String json = null;
		// 删除根节点
		TransportFormat ftransportFormat = TransportFormat.JSON;
		try {
			json = ftransportFormat.writeToJson(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		DBCollection dbCol = db.getCollection(collectionName);
		DBObject value =(DBObject)JSON.parse(json);
		dbCol.insert(value);
/*		document.clear();*/
	}

	public void disConnection() {
		mongo.close(); 
		mongo=null; 
	    db=null;
		 
	}


}