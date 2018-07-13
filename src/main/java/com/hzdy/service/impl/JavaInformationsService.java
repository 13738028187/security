package com.hzdy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

@Service("javaInformationsService")
public class JavaInformationsService {
	private Mongo m;
	private DB database = null;

	public List<String> queryAll() {
		connection();
		ArrayList<String> lists = new ArrayList<String>();
		database = m.getDB("java");
		DBCollection dbCol = database.getCollection("javaInfo");
		DBCursor ret = dbCol.find();
		while (ret.hasNext()) {
			BasicDBObject bdbObj = (BasicDBObject) ret.next();
			if (bdbObj != null) {
				lists.add(bdbObj.toString());
			}
		}
		ret.close();
		disconnection();
		/*
		 * m.close(); m=null;
		 */
		return lists;
	}
	public void connection(){
		try{
			m=new Mongo("127.0.0.1", 27017);
		}catch(Exception e){
			System.out.println("无法连接数据库");
		}
		
	}
	public void disconnection(){
		m.close();
	}
}
