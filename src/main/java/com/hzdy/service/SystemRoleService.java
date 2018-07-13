package com.hzdy.service;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.SystemRole;
import org.springframework.stereotype.Service;

/**
 * 角色 业务类
 * 
 * <p>文件名:SystemRoleService </p>
 * <p>描述: </p>
 * @author Administrator
 * @date 2017年9月5日 下午2:50:30
 * @version 1.0
 *
 */
@Service("systemRoleService")
public interface SystemRoleService {
	
	SystemRole queryObject(Integer roleId);
	
	List<SystemRole> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SystemRole role);
	
	void update(SystemRole role);
	
	void deleteBatch(Integer[] roleIds);

	/**
	 * 查询所有角色
	 * @return
	 */
	List<SystemRole> queryRoles();
	
	/**
	 * 插入角色
	 * @param systemRole
	 */
	boolean insertRole(SystemRole systemRole);

	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	Integer deleteRole(Integer id);
	
}
