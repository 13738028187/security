package com.hzdy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hzdy.entity.Priority;

/**
 * 菜单管理
 * <p>文件名:SysMenuDao </p>
 * <p>描述: </p>
 *
 * @author Administrator
 * @version 1.0
 * @date 2017年1月24日 下午2:54:12
 */
public interface PriorityDao extends BaseDao<Priority> {

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId     父菜单ID
     * @param priorityType 权限类型：0-系统后台用户权限，1-微信端用户权限
     */
    List<Priority> queryListParentId(@Param("parentId") Integer parentId, @Param("priorityType") Integer priorityType);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<Priority> queryNotButtonList();

    /**
     * 列出所有父权限
     *
     * @author ywm
     * @date 2017年9月24日
     */
    List<Priority> queryParentPriority();

    /**
     * 查找子权限
     *
     * @author ywm
     * @date 2017年9月25日
     */
    List<Priority> queryNodePriority(Integer parentId);
    
    /**
     * 根据权限名称查询ID
     * @param priority
     * @return
     */
    Integer queryIdByName(String priority);

}