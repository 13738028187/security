package com.hzdy.service.impl;

import com.hzdy.dao.SystemRoleDao;
import com.hzdy.entity.SystemRole;
import com.hzdy.service.SystemRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("systemRoleService")
public class SystemRoleServiceImpl implements SystemRoleService {
    @Resource
    private SystemRoleDao systemRoleDao;

    @Override
    public SystemRole queryObject(Integer roleId) {
        return null;
    }

    @Override
    public List<SystemRole> queryList(Map<String, Object> map) {
        return null;
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return 0;
    }

    @Override
    public void save(SystemRole role) {

    }

    @Override
    public void update(SystemRole role) {

    }

    @Override
    public void deleteBatch(Integer[] roleIds) {

    }

    @Override
    public List<SystemRole> queryRoles() {
        return systemRoleDao.queryRoles();
    }

	@Override
	public boolean insertRole(SystemRole systemRole) {
		// TODO Auto-generated method stub
		List<SystemRole> roles = systemRoleDao.queryRoles();
		for (SystemRole role : roles) {
			if(role.getRoleName().equals(systemRole.getRoleName()))
				return false;
		}
		Date createDate = new Date();
		systemRole.setCreateDate(createDate);
		systemRoleDao.insertRole(systemRole);
		return true;
	}

	@Transactional
    @Override
    public Integer deleteRole(Integer id) {
        try {
            systemRoleDao.deleteRole(id);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
