package com.hzdy.dao;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.SystemUser;

public interface SystemUserDao extends BaseDao<SystemUser> {

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     */
    List<String> queryAllPerms(Integer userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Integer> queryAllMenuId(Integer userId);

    /**
     * 根据用户名，查询系统用户
     */
    SystemUser queryByLoginName(String username);

    /**
     * 修改密码
     */
    int updatePassword(Map<String, Object> map);

    /**
     * @author ywm
     * 查询所有管理员
     */
    List<SystemUser> queryAllUser();

    /**
     * @author ywm
     * 查询管理员是否已经存在
     */
    SystemUser queryUserByloginNameAnduserName(String loginName);

    /**
     * @author lq
     * 添加新管理员
     */
    int insertSystemUser(SystemUser su);

    /**
     * 删除系统用户
     * @param id
     * @return
     */
    void deleteSystemUser(Integer id);

}