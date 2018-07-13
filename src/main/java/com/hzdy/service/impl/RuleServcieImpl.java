package com.hzdy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hzdy.dao.RuleDao;
import com.hzdy.entity.Rule;
import com.hzdy.service.RuleService;
@Service("ruleServcie")
public class RuleServcieImpl implements RuleService {
	@Resource
	private RuleDao ruleDao;
	@Override
	public List<Rule> queryList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ruleDao.queryList(map);
	}

	@Override
	public Rule queryRuleById(Integer id) {
		// TODO Auto-generated method stub
		return ruleDao.queryRuleById(id);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ruleDao.queryTotal(map);
	}

	@Override
	public int insert(Rule rule) {
		// TODO Auto-generated method stub
		return ruleDao.insert(rule);
	}

	@Override
	public int update(Rule rule) {
		// TODO Auto-generated method stub
		return ruleDao.update(rule);
	}

	@Override
	public int delete(String ids) {
		// TODO Auto-generated method stub
		try {
			String[] id = ids.split(";");
			for (int i = 0; i < id.length; i++) {
				ruleDao.delete(Integer.parseInt(id[i]));
			}
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}

}
