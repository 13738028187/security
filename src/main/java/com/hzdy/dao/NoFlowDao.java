package com.hzdy.dao;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.NoFlow;

/**
 * 无流量配置
 * @author zhangjun
 * @date 2017.11.2
 */
public interface NoFlowDao {
	
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
	
	int delete(int id);

	/**
	 * 更新策略状态
	 * @param id
	 * @return
	 */
	int updateStatus(int id, int status);
}
