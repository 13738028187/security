package com.hzdy.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hzdy.entity.Priority;

/**
 * 权限业务类
 * 
 * <p>文件名:PriorityService </p>
 * <p>描述: </p>
 * @author Administrator
 * @date 2017年9月5日 下午2:52:08
 * @version 1.0
 *
 */
@Service("priorityService")
public interface PriorityService {
	
	/**
	 * 菜单类型
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        private MenuType(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param priorityType 权限类型：0-系统后台用户权限，1-微信端用户权限
	 * @param menuIdList  用户菜单ID
	 */
	List<Priority> queryListParentId(Integer parentId,Integer priorityType, List<Integer> menuIdList);
	
	/**
	 * 后台 获取 非按钮的 权限菜单集合
	 * 获取不包含按钮的菜单列表
	 */
	List<Priority> queryNotButtonList();
	
	/**
	 * 获取用户菜单列表
	 * 
	 * @param userId 用户ID
	 * @param priorityType 权限类型：0-系统后台用户权限，1-微信端用户权限
	 * @return
	 */
	List<Priority> getUserMenuList(Integer userId,Integer priorityType);
	
	
	/**
	 * 查询菜单
	 */
	Priority queryObject(Integer menuId);
	
	/**
	 * 查询菜单列表
	 */
	List<Priority> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存菜单
	 */
	void save(Priority menu);
	
	/**
	 * 修改
	 */
	void update(Priority menu);
	
	/**
	 * 删除
	 */
	void deleteBatch(Integer[] menuIds);
	
}
