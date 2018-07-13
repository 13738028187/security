package com.hzdy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzdy.dao.KeyEventsDao;
import com.hzdy.entity.KeyEvents;
import com.hzdy.service.KeyEventsService;

@Service("keyEventsService")
public class KeyEventsServiceImpl implements KeyEventsService {
	@Resource
	private KeyEventsDao keyEventsDao;

	@Override
	public List<KeyEvents> queryList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return keyEventsDao.queryList(map);
	}

	@Override
	public KeyEvents queryKeyEventsById(Integer id) {
		// TODO Auto-generated method stub
		return keyEventsDao.queryKeyEventsById(id);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return keyEventsDao.queryTotal(map);
	}

	@Transactional
	@Override
	public int delete(String ids) {
		// TODO Auto-generated method stub
		try {
			String[] id = ids.split(";");
			for (int i = 0; i < id.length; i++) {
				keyEventsDao.delete(Integer.parseInt(id[i]));
			}
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int insert(KeyEvents keyEvents) {
		// TODO Auto-generated method stub
		return keyEventsDao.insert(keyEvents);
	}

	@Override
	public int update(KeyEvents keyEvents) {
		// TODO Auto-generated method stub
		return keyEventsDao.update(keyEvents);
	}

}
