package com.hzdy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzdy.dao.SecurityAreaConfigDao;
import com.hzdy.entity.SecurityAreaConfig;
import com.hzdy.service.SecurityAreaConfigService;

@Service("securityAreaConfigService")
public class SecurityAreaConfigServiceImpl implements SecurityAreaConfigService {
	
	@Resource
	private SecurityAreaConfigDao securityAreaConfigDao;

	@Override
	public List<SecurityAreaConfig> querySecurityAreaConfig(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return securityAreaConfigDao.querySecurityAreaConfig(map);
	}

	@Override
	public Integer queryTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return securityAreaConfigDao.queryTotal(map);
	}

	@Override
	public SecurityAreaConfig querySecurityConfigById(Integer configId) {
		// TODO Auto-generated method stub
		return securityAreaConfigDao.querySecurityConfigById(configId);
	}

	@Override
	public String insertSecurityAreaConfig(SecurityAreaConfig config) {
		// TODO Auto-generated method stub
		SecurityAreaConfig existConfig = securityAreaConfigDao.checkDuplication(config);
		if(existConfig == null) {
			securityAreaConfigDao.insertSecurityAreaConfig(config);
			return "添加成功";
		} else
			return "该配置已存在";
	}

	@Transactional
	@Override
	public String deleteSecurityAreaConfig(String[] configIds) {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < configIds.length; i++) {
				securityAreaConfigDao.deleteSecurityAreaConfig(Integer.parseInt(configIds[i]));
			}
			return "删除成功";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "删除失败";
		}
	}

	@Override
	public String updateSecurityAreaConfig(SecurityAreaConfig config) {
		// TODO Auto-generated method stub
		SecurityAreaConfig existConfig = securityAreaConfigDao.checkDuplication(config);
		if(existConfig == null) {
			securityAreaConfigDao.updateSecurityAreaConfig(config);
			return "修改成功";
		} else
			return "该配置已存在";
	}

	@Override
	public void deleteConfigBySecurityAreaId(Integer securityAreaId) {
		// TODO Auto-generated method stub
		securityAreaConfigDao.deleteConfigBySecurityAreaId(securityAreaId);
	}

}
