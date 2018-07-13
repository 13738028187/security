package com.hzdy.service;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.WebCount;

/**
 * 网络审计
 * @author zhangjun
 * @date 2017.11.2
 */
public interface WebCountService {
	
	/**
	 * 查询所有网络审计
	 * @param map
	 * @return
	 * @date 2017.11.2
	 */
	List<WebCount> queryList(Map<String, Object> map);
	
	/**
	 * 根据ID查询网络审计
	 * @param id
	 * @return
	 * @date 2017.11.2
	 */
	WebCount queryWebCountById(Integer id);
	
	/**
	 * 查询总数
	 * @param map
	 * @return
	 * @date 2017.11.2
	 */
	int queryTotal(Map<String, Object> map);
	
	int insert(WebCount webCount);
	
	int update(WebCount webCount);
	
	int delete(String id);
}
