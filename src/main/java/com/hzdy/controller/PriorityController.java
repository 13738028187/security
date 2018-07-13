package com.hzdy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hzdy.entity.Priority;
import com.hzdy.exception.CustomException;
import com.hzdy.service.PriorityService;
import com.hzdy.service.PriorityService.MenuType;
import com.hzdy.utils.PageUtils;
import com.hzdy.utils.R;

/**
 *  系统权限
 * <p>文件名:SysMenuController </p>
 * <p>描述: </p>
 * @author Administrator
 * @date 2017年9月6日 下午3:08:35
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/sys/menu")
public class PriorityController extends AbstractController {
	
	@Autowired
	private PriorityService priorityService;
	
	/**
	 * 所有菜单列表
	 */
	@RequestMapping("/list")
	/*@RequiresPermissions("sys:menu:list")*/
	public R list(Integer page, Integer limit,Integer priorityType){
		
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		if(priorityType!=null){
			map.put("priorityType", priorityType==-1?null:priorityType);
		}
		
		//查询列表数据
		List<Priority> menuList = priorityService.queryList(map);
		int total = priorityService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(menuList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@RequestMapping("/select")
	/*@RequiresPermissions("sys:menu:select")*/
	public R select(){
		//查询列表数据
		List<Priority> menuList = priorityService.queryNotButtonList();
		
		//添加顶级菜单
		Priority root = new Priority();
		root.setPriorityId(0);
		root.setName("一级菜单");
		root.setParentId(-1);
		root.setOpen(true);
		menuList.add(root);
		
		return R.ok().put("menuList", menuList);
	}
	
	/**
	 * 角色授权菜单
	 * @param type 权限类型：0-系统后台用户权限，1-微信端用户权限
	 */
	@RequestMapping("/perms/{type}")
	/*@RequiresPermissions("sys:menu:perms")*/
	public R perms(@PathVariable("type") Integer type){
		HashMap<String, Object> hash = new HashMap<String, Object>();
		if(type!=null){
			hash.put("priorityType", type);
		}
		
		//查询列表数据
		List<Priority> menuList = priorityService.queryList(hash);
		
		return R.ok().put("menuList", menuList);
	}
	
	/**
	 * 菜单信息
	 */
	@RequestMapping("/info/{menuId}")
	/*@RequiresPermissions("sys:menu:info")*/
	public R info(@PathVariable("menuId") Integer menuId){
		Priority menu = priorityService.queryObject(menuId);
		return R.ok().put("menu", menu);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	/*@RequiresPermissions("sys:menu:save")*/
	public R save(@RequestBody Priority menu){
		//数据校验
		verifyForm(menu);
		
		priorityService.save(menu);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	/*@RequiresPermissions("sys:menu:update")*/
	public R update(@RequestBody Priority menu){
		//数据校验
		verifyForm(menu);
				
		priorityService.update(menu);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	/*@RequiresPermissions("sys:menu:delete")*/
	public R delete(@RequestBody Integer[] menuIds){
		for(Integer menuId : menuIds){
			if(menuId.intValue() <= 28){
				return R.error("系统菜单，不能删除");
			}
		}
		priorityService.deleteBatch(menuIds);
		
		return R.ok();
	}
	
	/**
	 * 用户菜单列表
	 */
	@RequestMapping("/user")
	public R user(){
		
		List<Priority> menuList = priorityService.getUserMenuList(getUserId(),0);
		System.out.println(menuList);
		return R.ok().put("menuList", menuList);
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(Priority menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new CustomException("菜单名称不能为空");
		}
		
		if(menu.getParentId() == null){
			throw new CustomException("上级菜单不能为空");
		}
		
		//菜单
		if(menu.getNodeType() == MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new CustomException("菜单URL不能为空");
			}
		}
		
		//上级菜单类型
		int parentType = MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			Priority parentMenu = priorityService.queryObject(menu.getParentId());
			parentType = parentMenu.getNodeType();
		}
		
		//目录、菜单
		if(menu.getNodeType() == MenuType.CATALOG.getValue() ||
				menu.getNodeType() == MenuType.MENU.getValue()){
			if(parentType != MenuType.CATALOG.getValue()){
				throw new CustomException("上级菜单只能为目录类型");
			}
			return ;
		}
		
		//按钮
		if(menu.getNodeType() == MenuType.BUTTON.getValue()){
			if(parentType != MenuType.MENU.getValue()){
				throw new CustomException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
