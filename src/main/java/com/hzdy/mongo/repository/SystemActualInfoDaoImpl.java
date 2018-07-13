package com.hzdy.mongo.repository;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.hzdy.hardware.entity.SystemActualInfo;
import com.hzdy.mongo.dao.SystemActualInfoDao;

/**
* 
* what
* 
* @author kirohuji
* @version 0.1
*/
@Repository("systemActualInfoDao")  
public class SystemActualInfoDaoImpl implements SystemActualInfoDao{
	  
    @Resource  
    private MongoTemplate mongoTemplate;  
  
    @Override  
    public void insert(SystemActualInfo object,String collectionName) {  
        mongoTemplate.insert(object, collectionName);  
    }  
  
    @Override  
    public SystemActualInfo findOne(Map<String,Object> params,String collectionName,String query) {  
         return mongoTemplate.findOne(new Query(Criteria.where(query).is(params.get(query))), SystemActualInfo.class,collectionName);    
    }  
  
    @Override  
    public List<SystemActualInfo> findAll(Map<String,Object> params,String collectionName) {  
        List<SystemActualInfo> result = mongoTemplate.findAll(SystemActualInfo.class, collectionName);
        return result;  
    }  
  
    @Override  
    public void update(Map<String,Object> params,String collectionName,String query,String value) {  
        mongoTemplate.upsert(new Query(Criteria.where(query).is(params.get(query))), new Update().set(value, params.get(value)), SystemActualInfo.class,collectionName);  
    }  
  
    @Override  
    public void createCollection(String name) {  
        mongoTemplate.createCollection(name);  
    }  
  
  
    @Override  
    public void remove(Map<String, Object> params,String collectionName) {  
		mongoTemplate.remove(new Query(Criteria.where("id").is(params.get("id"))), SystemActualInfo.class,collectionName);  
    }

	@Override
	public SystemActualInfo findOne(Map<String, Object> params, String collectionName, String query, String projection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SystemActualInfo> findAll(Map<String, Object> params, String collectionName, String projection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SystemActualInfo> findList(Map<String, Object> params, String collectionName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SystemActualInfo findList(Map<String, Object> params, String collectionName, String query,
			String projection) {
		// TODO Auto-generated method stub
		return null;
	}  
}
