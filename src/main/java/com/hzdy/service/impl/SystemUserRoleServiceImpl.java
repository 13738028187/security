package com.hzdy.service.impl;

import java.util.List;

import com.hzdy.dao.SystemUserRoleDao;
import com.hzdy.entity.SystemRole;
import com.hzdy.entity.SystemUser;
import com.hzdy.entity.SystemUserRole;
import com.hzdy.service.SystemUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统用户角色
 * <p>
 * <p>文件名:SystemUserRoleService </p>
 * <p>描述: </p>
 *
 * @author Administrator
 * @version 1.0
 * @date 2017年9月4日 下午2:59:17
 */
@Service("systemUserRoleService")
public class SystemUserRoleServiceImpl implements SystemUserRoleService {

    @Resource
    private SystemUserRoleDao systemUserRoleDao;

    @Override
    public void saveOrUpdate(Integer userId, List<Integer> roleIdList) {

    }

    @Override
    public List<SystemUserRole> queryRoleList() {
        return null;
    }

    @Override
    public List<Integer> queryRoleIdList(Integer userId) {
        return null;
    }
    
    @Override
	public void delete(Integer userId) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void modifyManagerRole(Integer userid, String[] roleId, String[] systemRoles) {
        List<Integer> allRoles = systemUserRoleDao.queryRoleIdList(userid);

        //判断提交的角色修改，如果取消原选项，则删除数据库中的相应数据，如果是新选项，则插入新数据
        for (int i = 0; i < systemRoles.length; i++) {
        	if(allRoles.contains(Integer.parseInt(roleId[i])) == true && "false".equals(systemRoles[i]))
                systemUserRoleDao.deleteSystemUserRole(userid, Integer.parseInt(roleId[i]));
            else if(allRoles.contains(Integer.parseInt(roleId[i])) == false && "true".equals(systemRoles[i]))
                systemUserRoleDao.insertSystemUserRole(userid, Integer.parseInt(roleId[i]));
        }
        /*if(roles.contains(1) == true && "false".equals(systemManager))
            systemUserRoleDao.deleteSystemUserRole(userid, 1);
        else if(roles.contains(1) == false && "true".equals(systemManager))
            systemUserRoleDao.insertSystemUserRole(userid, 1);

        if(roles.contains(2) == true && "false".equals(securityOfficer))
            systemUserRoleDao.deleteSystemUserRole(userid, 2);
        else if(roles.contains(2) == false && "true".equals(securityOfficer))
            systemUserRoleDao.insertSystemUserRole(userid, 2);

        if(roles.contains(3) == true && "false".equals(auditor))
            systemUserRoleDao.deleteSystemUserRole(userid, 3);
        else if(roles.contains(3) == false && "true".equals(auditor))
            systemUserRoleDao.insertSystemUserRole(userid, 3);

        if(roles.contains(4) == true && "false".equals(normalManager))
            systemUserRoleDao.deleteSystemUserRole(userid, 4);
        else if(roles.contains(4) == false && "true".equals(normalManager))
            systemUserRoleDao.insertSystemUserRole(userid, 4);*/
    }

}
