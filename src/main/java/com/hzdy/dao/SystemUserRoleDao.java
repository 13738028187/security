package com.hzdy.dao;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.SystemRole;
import com.hzdy.entity.SystemUserRole;

public interface SystemUserRoleDao extends BaseDao<SystemUserRole> {
    
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Integer> queryRoleIdList(Integer userId);
    
	/**
	 * 批量插入数据
	 * @param map
	 */
	void saveBatch(Map<String, Object> map);

	/**
	 * @author ywm
	 * 插入管理员新角色
	 */
	int insertSystemUserRole(Integer userid,Integer roleid);

	/**
	 * @author ywm
	 * 删除管理员角色
	 */
	void deleteSystemUserRole(Integer userid,Integer roleid);
	
}