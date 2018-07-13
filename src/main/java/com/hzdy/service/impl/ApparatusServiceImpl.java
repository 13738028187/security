package com.hzdy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hzdy.dao.ApparatusDao;
import com.hzdy.discovery.Device;
import com.hzdy.entity.Apparatus;
import com.hzdy.entity.Priority;
import com.hzdy.service.ApparatusService;

@Service("apparatusService")
public class ApparatusServiceImpl implements ApparatusService {

	@Resource
	private ApparatusDao ApparatusDao;

	@Override
	public int save(Apparatus t) {
		// TODO Auto-generated method stub
		return ApparatusDao.save(t);
	}

	@Override
	public int update(Apparatus t) {
		// TODO Auto-generated method stub
		return ApparatusDao.update(t);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return ApparatusDao.delete(id);
	}

	@Override
	public int deleteBatch(Object[] id) {
		// TODO Auto-generated method stub
		return ApparatusDao.deleteBatch(id);
	}

	@Override
	public Apparatus queryObject(Object id) {
		// TODO Auto-generated method stub
		return ApparatusDao.queryObject(id);
	}

	@Override
	public int queryTotal() {
		// TODO Auto-generated method stub
		return ApparatusDao.queryTotal();
	}

	@Override
	public ArrayList<Apparatus> queryAll() {
		// TODO Auto-generated method stub
		return ApparatusDao.queryAll();
	}

	@Override
	public List<Apparatus> queryList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ApparatusDao.queryList(map);
	}
	@Override
	public int queryTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ApparatusDao.queryTotal(map);
	}
	

}
