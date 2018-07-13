package com.hzdy.dao;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.SystemRolePriority;

public interface SystemRolePriorityDao extends BaseDao<SystemRolePriority> {

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Integer> queryMenuIdList(Integer roleId);

    /**
     * 批量保存数据
     *
     * @param map
     */
    void saveBatch(Map<String, Object> map);

    /**
     * 插入父权限
     *
     * @param roleId
     * @param parentId
     * @author ywm
     */
    void insertParentPriority(Integer roleId, Integer parentId);

    /**
     * 插入子权限
     *
     * @param roleId
     * @param nodePriorityId
     * @author ywm
     */
    void insertNodePriority(Integer roleId, List<Integer> nodePriorityId);

    /**
     * 删除父权限
     *
     * @param roleId
     * @param parentId
     * @author ywm
     */
    void deleteParentPriority(Integer roleId, Integer parentId);

    /**
     * 删除子权限
     *
     * @param roleId
     * @param parentId
     * @author ywm
     */
    void deleteNodePriority(Integer roleId, Integer parentId);
    
    void delete(Integer roleId);
    
    void insert(Integer roleId, Integer priorityId);

}