package com.hzdy.dao;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.Equipment;

/**
 * 设备管理
 * @author yuwenming
 * @date 2017.10.13
 */
public interface EquipmentDao extends BaseDao<Equipment>{
	
	/**
	 * 查询设备信息
	 * @param map
	 * @return
	 * @date 2017.10.13
	 */
	List<Equipment> queryEquipment(Map<String, Object> map);
	
	/**
	 * 根据ID查询设备信息
	 * @param id
	 * @return
	 * @date 2017.10.14
	 */
	Equipment queryEquipmentById(Integer id);
	
	List<Equipment> queryAll();
	
	List<Equipment> queryList(Map<String, Object> map);
	
	int queryTotalByMap(Map<String, Object> map);
}
