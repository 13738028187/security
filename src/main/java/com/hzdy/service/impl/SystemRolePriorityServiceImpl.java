package com.hzdy.service.impl;

import com.hzdy.dao.PriorityDao;
import com.hzdy.dao.SystemRolePriorityDao;
import com.hzdy.entity.Priority;
import com.hzdy.service.SystemRolePriorityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统 角色-权限 业务类
 * 
 * <p>
 * 文件名:SystemRolePriorityServiceImpl
 * </p>
 * <p>
 * 描述:
 * </p>
 * 
 * @author Administrator
 * @date 2017年9月4日 下午3:00:53
 * @version 1.0
 *
 */
@Service("systemRolePriorityService")
public class SystemRolePriorityServiceImpl implements SystemRolePriorityService {

	@Resource
	private SystemRolePriorityDao systemRolePriorityDao;
	@Resource
	private PriorityDao priorityDao;

	@Override
	public void saveOrUpdate(Integer roleId, List<Integer> menuIdList) {

	}

	@Override
	public List<Integer> queryMenuIdList(Integer roleId) {
		return null;
	}

	@Transactional
	@Override
	public String modifySystemRolePriority(Integer roleId, String priority) {
		try {
			systemRolePriorityDao.delete(roleId);
			if(priority == null || "".equals(priority))
				return "修改成功";
			else {
				String[] priorities = priority.split(";");
				for (int i = 0; i < priorities.length; i++) {
					Integer priorityId = priorityDao.queryIdByName(priorities[i]);
					systemRolePriorityDao.insert(roleId, priorityId);
					List<Priority> nodePriority = priorityDao.queryNodePriority(priorityId);
					for (int j = 0; j < nodePriority.size(); j++) {
						systemRolePriorityDao.insert(roleId, nodePriority.get(j).getPriorityId());
					}
				}
				return "修改成功";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "修改失败";
		}

	}
}
