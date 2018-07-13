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

import com.hzdy.discovery.entity.json.NetworkGraph;
import com.hzdy.mongo.dao.NetworkGraphDao;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;

/**
* 
* what
* 
* @author kirohuji
* @version 0.1
*/
@Repository("networkGraphDao")  
public class NetworkGraphDaoImpl implements NetworkGraphDao{
	  
    @Resource  
    private MongoTemplate mongoTemplate;  
  
    @Override  
    public void insert(NetworkGraph object,String collectionName) {  
        mongoTemplate.insert(object, collectionName);  
    }  
  
    @Override  
    public NetworkGraph findOne(Map<String,Object> params,String collectionName,String query) {  
         return mongoTemplate.findOne(new Query(Criteria.where(query).is(params.get(query))), NetworkGraph.class,collectionName);    
    }  
  
    @Override  
    public List<NetworkGraph> findAll(Map<String,Object> params,String collectionName) {  
        List<NetworkGraph> result = mongoTemplate.findAll(NetworkGraph.class, collectionName);
        return result;  
    }  
  
    @Override  
    public void update(Map<String,Object> params,String collectionName,String query,String value) {  
        mongoTemplate.upsert(new Query(Criteria.where(query).is(params.get(query))), new Update().set(value, params.get(value)), NetworkGraph.class,collectionName);  
    }  
  
    @Override  
    public void createCollection(String name) {  
        mongoTemplate.createCollection(name);  
    }  
  
  
    @Override  
    public void remove(Map<String, Object> params,String collectionName) {  
		mongoTemplate.remove(new Query(Criteria.where("author").is(params.get("author"))), NetworkGraph.class,collectionName);  
    }

	@Override
	public NetworkGraph findOne(Map<String, Object> params, String collectionName, String query, String projection) {
		BasicDBObject fieldsObject=new BasicDBObject();
		fieldsObject.put(projection, 1);
		return mongoTemplate.findOne(new BasicQuery(fieldsObject), NetworkGraph.class,collectionName); 
	}

	@Override
	public List<NetworkGraph> findAll(Map<String, Object> params, String collectionName, String projection) {
		BasicDBObject fieldsObject=new BasicDBObject();
		fieldsObject.put(projection, 1);
		return mongoTemplate.find(new BasicQuery(fieldsObject), NetworkGraph.class,collectionName);
	}
	
	@Override
	public <T> void push(String collectionName,String key, T value) {
		mongoTemplate.upsert(new Query(), new Update().push(key, value),NetworkGraph.class,collectionName);
	}

	@Override
	public List<NetworkGraph> findList(Map<String, Object> params, String collectionName) {
		return mongoTemplate.find(new Query(Criteria.where("author").is(params.get("author"))), NetworkGraph.class,collectionName);
	}

	@Override
	public NetworkGraph findList(Map<String, Object> params, String collectionName, String query, String projection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NetworkGraph> findAll(String collectionName) {
        List<NetworkGraph> result = mongoTemplate.findAll(NetworkGraph.class, collectionName);
        return result;  
	}  
    
}
