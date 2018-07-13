package com.hzdy.mongo.dao;

import java.util.List;
import java.util.Map;

import com.hzdy.discovery.entity.json.NetworkGraph;

/**
 * 
 * what
 * 
 * @author kirohuji
 * @version 0.1
 */
public interface MongoBase<T> {
	// 添加
	public void insert(T object, String collectionName);

	// 根据条件查找
	public T findOne(Map<String, Object> params, String collectionName, String query);

	// 根据条件查找一组记录
	public List<T> findList(Map<String, Object> params, String collectionName);

	// 查找所有
	public List<T> findAll(Map<String, Object> params, String collectionName);

	// 根据条件查找
	public T findOne(Map<String, Object> params, String collectionName, String query, String projection);

	// 根据条件查找一组记录
	public T findList(Map<String, Object> params, String collectionName, String query, String projection);

	// 查找所有
	public List<T> findAll(Map<String, Object> params, String collectionName, String projection);

	// 修改
	public void update(Map<String, Object> params, String collectionName, String query, String value);

	// 创建集合
	public void createCollection(String collectionName);

	// 根据条件删除
	public void remove(Map<String, Object> params, String collectionName);

}
