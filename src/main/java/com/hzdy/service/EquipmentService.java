package com.hzdy.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hzdy.discovery.Ip;
import com.hzdy.entity.Equipment;

/**
 * 设备业务类
 * 
 * @author yuwenming
 * @date 2017.10.13
 */

public interface EquipmentService {

	/**
	 * 查询设备信息
	 * 
	 * @param map
	 * @return
	 * @date 2017.10.13
	 */
	List<Equipment> queryEquipment(Map<String, Object> map);

	/**
	 * 根据ID查询设备信息
	 * 
	 * @param id
	 * @return
	 * @date 2017.10.14
	 */
	Equipment queryEquipmentById(Integer id);

	public int save(Equipment t);

	public int update(Equipment t);

	public int delete(int id);

	public int deleteBatch(Object[] id);

	public Equipment queryObject(Object id);

	public int queryTotal();
	
	public List<Equipment> queryAll();
	
	List<Equipment> queryList(Map<String, Object> map);
	
	int queryTotalByMap(Map<String, Object> map);
}
