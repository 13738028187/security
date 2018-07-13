package com.hzdy.mongo.repository;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.hzdy.discovery.entity.json.FixNetworkGraph;
import com.hzdy.mongo.dao.FixNetworkGraphDao;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;

/**
* 
* what
* 
* @author kirohuji
* @version 0.1
*/
@Repository("fixNetworkGraphDao")  
public class FixNetworkGraphDaoImpl implements FixNetworkGraphDao{
	  
    @Resource  
    private MongoTemplate mongoTemplate;  
  
    @Override  
    public void insert(FixNetworkGraph object,String collectionName) {  
        mongoTemplate.insert(object, collectionName);  
    }  
  
    @Override  
    public FixNetworkGraph findOne(Map<String,Object> params,String collectionName,String query) {  
         return mongoTemplate.findOne(new Query(Criteria.where(query).is(params.get(query))), FixNetworkGraph.class,collectionName);    
    }  
  
    @Override  
    public List<FixNetworkGraph> findAll(Map<String,Object> params,String collectionName) {  
        List<FixNetworkGraph> result = mongoTemplate.findAll(FixNetworkGraph.class, collectionName);
        return result;  
    }  
  
    @Override  
    public void update(Map<String,Object> params,String collectionName,String query,String value) {  
        mongoTemplate.upsert(new Query(Criteria.where(query).is(params.get(query))), new Update().set(value, params.get(value)), FixNetworkGraph.class,collectionName);  
    }  
  
    @Override  
    public void createCollection(String name) {  
        mongoTemplate.createCollection(name);  
    }  
  
  
    @Override  
    public void remove(Map<String, Object> params,String collectionName) {  
		mongoTemplate.remove(new Query(Criteria.where("author").is(params.get("author"))), FixNetworkGraph.class,collectionName);  
    }

	@Override
	public FixNetworkGraph findOne(Map<String, Object> params, String collectionName, String query, String projection) {
		BasicDBObject fieldsObject=new BasicDBObject();
		fieldsObject.put(projection, 1);
		return mongoTemplate.findOne(new BasicQuery(fieldsObject), FixNetworkGraph.class,collectionName); 
	}

	@Override
	public List<FixNetworkGraph> findAll(Map<String, Object> params, String collectionName, String projection) {
		BasicDBObject fieldsObject=new BasicDBObject();
		fieldsObject.put(projection, 1);
		return mongoTemplate.find(new BasicQuery(fieldsObject), FixNetworkGraph.class,collectionName);
	}
	
	@Override
	public <T> void push(String collectionName,String key, T value) {
		mongoTemplate.upsert(new Query(), new Update().push(key, value),FixNetworkGraph.class,collectionName);
	}

	@Override
	public List<FixNetworkGraph> findList(Map<String, Object> params, String collectionName) {
		return mongoTemplate.find(new Query(Criteria.where("author").is(params.get("author"))), FixNetworkGraph.class,collectionName);
	}

	@Override
	public FixNetworkGraph findList(Map<String, Object> params, String collectionName, String query, String projection) {
		// TODO Auto-generated method stub
		return null;
	}  
    
}
