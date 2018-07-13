package com.hzdy.dao;

import com.hzdy.entity.SystemRole;

import java.util.List;

/**
 * 系统角色 Dao 类
 * <p>
 * <p>文件名:SystemRoleDao </p>
 * <p>描述: </p>
 *
 * @author Administrator
 * @version 1.0
 * @date 2017年9月4日 下午8:38:29
 */
public interface SystemRoleDao extends BaseDao<SystemRole> {

    /**
     * 查找所有可用系统角色
     * @return
     */
    List<SystemRole> queryRoles();

    /**
     * 根据角色名称查找角色ID
     * @param roleName
     * @return
     */
    int queryIdByName(String roleName);

    /**
     * 根据角色ID查找角色名称
     * @param roleId
     * @return
     */
    String queryNameById(Integer roleId);
    
    /**
     * 添加角色
     * @param systemRole
     */
    void insertRole(SystemRole systemRole);

    /**
     * 删除角色
     * @param id
     */
    void deleteRole(Integer id);

}