package com.hzdy.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统 角色-权限 业务类
 * <p>
 * <p>文件名:SystemRolePriorityServiceImpl </p>
 * <p>描述: </p>
 *
 * @author Administrator
 * @version 1.0
 * @date 2017年9月5日 下午3:00:53
 */
@Service("systemRolePriorityService")
public interface SystemRolePriorityService {

    void saveOrUpdate(Integer roleId, List<Integer> menuIdList);

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Integer> queryMenuIdList(Integer roleId);

    /**
     * 修改角色权限
     *
     * @param roleId
     * @author ywm
     */
    String modifySystemRolePriority(Integer roleId, String priority);

}
