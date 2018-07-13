package com.hzdy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hzdy.dao.WebCountDao;
import com.hzdy.entity.WebCount;
import com.hzdy.service.WebCountService;

@Service("webCountServcie")
public class WebCountServcieImpl implements WebCountService {
	@Resource
	private WebCountDao webCountDao;

	@Override
	public List<WebCount> queryList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return webCountDao.queryList(map);
	}

	@Override
	public WebCount queryWebCountById(Integer id) {
		// TODO Auto-generated method stub
		return webCountDao.queryWebCountById(id);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return webCountDao.queryTotal(map);
	}

	@Override
	public int insert(WebCount webCount) {
		// TODO Auto-generated method stub
		return webCountDao.insert(webCount);
	}

	@Override
	public int update(WebCount webCount) {
		// TODO Auto-generated method stub
		return webCountDao.update(webCount);
	}

	@Override
	public int delete(String ids) {
		// TODO Auto-generated method stub
		try {
			String[] id = ids.split(";");
			for (int i = 0; i < id.length; i++) {
				webCountDao.delete(Integer.parseInt(id[i]));
			}
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}

}
