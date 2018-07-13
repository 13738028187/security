package com.hzdy.service;

import java.util.List;

import com.hzdy.entity.SystemUserRole;

/**
 * 系统用户角色
 * <p>
 * <p>文件名:SystemUserRoleService </p>
 * <p>描述: </p>
 *
 * @author Administrator
 * @version 1.0
 * @date 2017年9月5日 下午2:59:17
 */
public interface SystemUserRoleService {

    void saveOrUpdate(Integer userId, List<Integer> roleIdList);

    public List<SystemUserRole> queryRoleList();

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Integer> queryRoleIdList(Integer userId);

    void delete(Integer userId);

    /**
     * @author ywm
     * 修改管理员角色
     */
    void modifyManagerRole(Integer userid, String[] roleId, String[] systemRoles);

}
