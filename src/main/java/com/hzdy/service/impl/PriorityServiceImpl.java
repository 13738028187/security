package com.hzdy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzdy.dao.PriorityDao;
import com.hzdy.entity.Priority;
import com.hzdy.service.PriorityService;
import com.hzdy.service.SystemUserService;

@Service("priorityService")
public class PriorityServiceImpl implements PriorityService{

	@Resource
	private PriorityDao priorityDao;
	
	@Resource
	private SystemUserService systemUserService;
	
	
	@Override
	public List<Priority> queryListParentId(Integer parentId,Integer priorityType,List<Integer> menuIdList) {
		
		List<Priority> menuList = priorityDao.queryListParentId(parentId,priorityType);
		if(menuIdList == null){
			return menuList;
		}
		
		List<Priority> userMenuList = new ArrayList<>();
		for(Priority menu : menuList){
			if(menuIdList.contains(menu.getPriorityId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<Priority> queryNotButtonList() {
		
		return priorityDao.queryNotButtonList();
	}

	@Override
	public List<Priority> getUserMenuList(Integer userId,Integer priorityType) {
		
		//获取用户权限菜单
		List<Integer> menuIdList = systemUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList,priorityType);
	}

	@Override
	public Priority queryObject(Integer menuId) {
		
		return priorityDao.queryObject(menuId);
	}

	@Override
	public List<Priority> queryList(Map<String, Object> map) {
		
		return priorityDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		
		return priorityDao.queryTotal(map);
	}

	@Override
	public void save(Priority menu) {
		priorityDao.save(menu);
	}

	@Override
	public void update(Priority menu) {
		priorityDao.update(menu);
	}

	@Override
	@Transactional
	public void deleteBatch(Integer[] menuIds) {
	
		priorityDao.deleteBatch(menuIds);
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<Priority> getAllMenuList(List<Integer> menuIdList,Integer priorityType){
		//查询根菜单列表
		List<Priority> menuList = queryListParentId(0,priorityType, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList,priorityType, menuIdList);

		return menuList;
	}

	/**
	 * 递归
	 */
	private List<Priority> getMenuTreeList(List<Priority> menuList,Integer priorityType, List<Integer> menuIdList){
		List<Priority> subMenuList = new ArrayList<Priority>();
		
		for(Priority entity : menuList){
			if(entity.getNodeType() == MenuType.CATALOG.getValue()){//目录
				entity.setList(getMenuTreeList(queryListParentId(entity.getPriorityId(),priorityType, menuIdList),priorityType, menuIdList));
			}
			subMenuList.add(entity);
		}
		return subMenuList;
	}
	
}
