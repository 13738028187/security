package com.hzdy.mongo.repository;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.hzdy.discovery.entity.json.NetworkGraph;
import com.hzdy.logger.entity.Syslog;
import com.hzdy.mongo.dao.SyslogDao;

/**
* 
* what
* 
* @author kirohuji
* @version 0.1
*/
@Repository("syslogDao")  
public class SyslogDaoImpl implements SyslogDao{
	  
    @Resource  
    private MongoTemplate mongoTemplate;  
  
    @Override  
    public void insert(Syslog object,String collectionName) {  
        mongoTemplate.insert(object, collectionName);  
    }  
  
    @Override  
    public Syslog findOne(Map<String,Object> params,String collectionName,String query) {  
         return mongoTemplate.findOne(new Query(Criteria.where(query).is(params.get(query))), Syslog.class,collectionName);    
    }  
  
    @Override  
    public List<Syslog> findAll(Map<String,Object> params,String collectionName) {  
        List<Syslog> result = mongoTemplate.findAll(Syslog.class, collectionName);
        return result;  
    }  
  
    @Override  
    public void update(Map<String,Object> params,String collectionName,String query,String value) {  
        mongoTemplate.upsert(new Query(Criteria.where(query).is(params.get(query))), new Update().set(value, params.get(value)), Syslog.class,collectionName);  
    }  
  
    @Override  
    public void createCollection(String name) {  
        mongoTemplate.createCollection(name);  
    }  
  
  
    @Override  
    public void remove(Map<String, Object> params,String collectionName) {  
		mongoTemplate.remove(new Query(Criteria.where("id").is(params.get("id"))), Syslog.class,collectionName);  
    }

	@Override
	public Syslog findOne(Map<String, Object> params, String collectionName, String query, String projection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Syslog> findAll(Map<String, Object> params, String collectionName, String projection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Syslog> findList(Map<String, Object> params, String collectionName) {
		 Query query = new Query();
		 for(Entry<String,Object> entry:params.entrySet()) {
			  Criteria criteria = Criteria.where(entry.getKey()).is(entry.getValue());
			  query.addCriteria(criteria);
		 }
		return mongoTemplate.find(query, Syslog.class,collectionName);
	}

	@Override
	public Syslog findList(Map<String, Object> params, String collectionName, String query, String projection) {
		return null;
	}  
    
}
