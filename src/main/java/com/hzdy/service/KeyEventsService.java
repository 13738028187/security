package com.hzdy.service;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.KeyEvents;

/**
 * 关键事件类
 * @author zhangjun
 * @date 2017.10.31
 */
public interface KeyEventsService {

	/**
	 * 查询关键事件
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
	
	int queryTotal(Map<String, Object> map);
	
	int delete(String ids);
	
	int insert(KeyEvents keyEvents);
	
	int update(KeyEvents keyEvents);
	
}
