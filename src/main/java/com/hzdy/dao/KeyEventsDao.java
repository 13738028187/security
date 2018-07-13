package com.hzdy.dao;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.KeyEvents;

/**
 * 关键事件
 * @author zhangjun
 * @date 2017.10.31
 */
public interface KeyEventsDao {
	
	/**
	 * 查询所有关键事件
	 * @param map
	 * @return
	 * @date 2017.10.31
	 */
	List<KeyEvents> queryList(Map<String, Object> map);
	
	/**
	 * 根据ID查询关键事件
	 * @param id
	 * @return
	 * @date 2017.10.31
	 */
	KeyEvents queryKeyEventsById(Integer id);
	
	/**
	 * 查询总数
	 * @param map
	 * @return
	 * @date 2017.10.31
	 */
	int queryTotal(Map<String, Object> map);
	
	int insert(KeyEvents keyEvents);
	
	int update(KeyEvents keyEvents);
	
	int delete(int id);
}
