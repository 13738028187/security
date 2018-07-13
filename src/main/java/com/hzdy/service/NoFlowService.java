package com.hzdy.service;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.NoFlow;

/**
 * 无流量配置
 * @author zhangjun
 * @date 2017.11.2
 */
public interface NoFlowService {
	
	/**
	 * 查询所有无流量配置
	 * @param map
	 * @return
	 * @date 2017.11.2
	 */
	List<NoFlow> queryList(Map<String, Object> map);
	
	/**
	 * 根据ID查询无流量配置
	 * @param id
	 * @return
	 * @date 2017.11.2
	 */
	NoFlow queryNoFlowById(Integer id);
	
	/**
	 * 查询总数
	 * @param map
	 * @return
	 * @date 2017.11.2
	 */
	int queryTotal(Map<String, Object> map);
	
	int insert(NoFlow noFlow);
	
	int update(NoFlow noFlow);
	
	int delete(String ids);
	
	/**
	 * 执行无流量监测策略
	 * @param noFlow
	 * @return
	 */
	String execute(NoFlow noFlow);
	
	/**
	 * 停止执行无流量监测策略
	 * @param noFlow
	 */
	String stop(NoFlow noFlow);
	
}
