package com.hzdy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hzdy.dao.EquipmentDao;
import com.hzdy.discovery.Ip;
import com.hzdy.entity.Equipment;
import com.hzdy.service.EquipmentService;

@Service("equipmentService")
public class EquipmentServiceImpl implements EquipmentService {
	
	@Resource
	private EquipmentDao equipmentDao;

	@Override
	public List<Equipment> queryEquipment(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return equipmentDao.queryEquipment(map);
	}

	@Override
	public Equipment queryEquipmentById(Integer id) {
		// TODO Auto-generated method stub
		return equipmentDao.queryEquipmentById(id);
	}

	@Override
	public int save(Equipment t) {
		return equipmentDao.save(t);
	}

	@Override
	public int update(Equipment t) {
		return equipmentDao.update(t);
	}

	@Override
	public int delete(int id) {
		return equipmentDao.delete(id);
	}

	@Override
	public int deleteBatch(Object[] id) {
		return equipmentDao.deleteBatch(id);
	}

	@Override
	public Equipment queryObject(Object id) {
		return equipmentDao.queryObject(id);
	}

	@Override
	public int queryTotal() {
		return equipmentDao.queryTotal();
	}

	@Override
	public List<Equipment> queryAll() {
		// TODO Auto-generated method stub
		return equipmentDao.queryAll();
	}

	@Override
	public List<Equipment> queryList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return equipmentDao.queryList(map);
	}

	@Override
	public int queryTotalByMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return equipmentDao.queryTotal();
	}
	
}
