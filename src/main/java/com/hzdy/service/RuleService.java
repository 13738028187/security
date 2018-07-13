package com.hzdy.service;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.Rule;

/**
 * 规约配置
 * @author zhangjun
 * @date 2017.11.2
 */
public interface RuleService {
	
	/**
	 * 查询所有规约配置
	 * @param map
	 * @return
	 * @date 2017.11.2
	 */
	List<Rule> queryList(Map<String, Object> map);
	
	/**
	 * 根据ID查询规约配置
	 * @param id
	 * @return
	 * @date 2017.11.2
	 */
	Rule queryRuleById(Integer id);
	
	/**
	 * 查询总数
	 * @param map
	 * @return
	 * @date 2017.11.2
	 */
	int queryTotal(Map<String, Object> map);
	
	int insert(Rule rule);
	
	int update(Rule rule);
	
	int delete(String ids);
}
