package com.hzdy.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.hzdy.entity.SystemUser;

/**
 * 系统用户 业务类
 * <p>
 * <p>文件名:SystemUserService </p>
 * <p>描述: </p>
 *
 * @author Administrator
 * @version 1.0
 * @date 2017年9月5日 下午2:49:23
 */
@Service("systemUserService")
public interface SystemUserService {

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
     * 根据用户ID，查询用户
     *
     * @param userId
     * @return
     */
    SystemUser queryObject(Integer userId);

    /**
     * 查询用户列表
     */
    List<SystemUser> queryList(Map<String, Object> map);

    /**
     * 查询总数
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存用户
     */
    void save(SystemUser user) throws IllegalArgumentException;

    /**
     * 修改用户
     */
    void update(SystemUser user) throws IllegalArgumentException;

    /**
     * 删除用户
     */
    void deleteBatch(Integer[] userIds);

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param password    原密码
     * @param newPassword 新密码
     */
    int updatePassword(Integer userId, String password, String newPassword);

    /**
     * 插入系统用户
     * @param request
     * @return
     */
    SystemUser insertSystemUser(HttpServletRequest request);

    /**
     * 查询所有管理员
     */
    List<SystemUser> queryAllUser();

    /**
     * 删除系统用户
     * @param id
     * @return
     */
    Integer deleteSystemUser(Integer id);
    
}