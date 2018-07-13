package com.hzdy.dao;

import java.util.List;
import java.util.Map;

/**
 * 基础Dao(还需在XML文件里，有对应的SQL语句)
 * 通用的 基础 方法，其他dao继承，避免代码冗余 
 * 
 * <p>文件名:BaseDao </p>
 * <p>描述: </p>
 * @param <T>
 * @author ZhouGuang
 * @date 2017年8月28日 下午2:53:02
 * @version 1.0
 */
public interface BaseDao<T> {
	
	int save(T t);//插入数据
	
	int update(T t);//更新数据
	
	int delete(Object id);//根据主键ID 删除 
	
	int deleteBatch(Object[] id);//根据条件批量删除 

	T queryObject(Object id);//根据条件查询单条记录
	
	List<T> queryList(Object id);//根据不同条件 查询 数据
	
	List<T> queryList(Map<String, Object> map);//根据 不同 条件 分页查询
	
	int queryTotal(Map<String, Object> map);//根据不同条件获取 总记录数据

	int queryTotal();//获取总记录数
}
