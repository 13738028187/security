package com.hzdy.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hzdy.service.EmailService;
import com.hzdy.service.SystemUserRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzdy.entity.Equipment;
import com.hzdy.entity.SystemUser;
import com.hzdy.redis.dao.IpBlack;
import com.hzdy.redis.service.IpBlackService;
import com.hzdy.service.SystemUserService;
import com.hzdy.utils.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
	@Resource
	private IpBlackService ipBlackService;
	@Resource
	private EmailService emailService;
	@Resource
	private SystemUserService systemUserService;
	@Resource
	private SystemUserRoleService systemUserRoleService;

	// 添加新管理员
	@ResponseBody
	@RequestMapping(value = "/addSysUser", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	public Map<String, Object> addSysUser(HttpServletRequest request) {
		SystemUser su = systemUserService.insertSystemUser(request);
		Map<String, Object> map = new HashMap<>();
		if (su != null) {
			map.put("message", "success");
			map.put("user", su);
		} else {
			map.put("message", "登录名已经存在");
		}
		return map;
	}

	// 列出所有管理员
	@ResponseBody
	@RequestMapping(value = "/listAllUser", method = RequestMethod.GET)
	public String listAllUser() {

		List<SystemUser> allUser = systemUserService.queryAllUser();

		return JSON.toJSONString(allUser);

	}

	// 分页
	@ResponseBody
	@RequestMapping(value = "/limitListUser", method = RequestMethod.GET)
	public JSONObject limitListUser(@RequestParam(value = "page", defaultValue = "1") String page,
			@RequestParam(value = "rows", defaultValue = "10") String rows) {

		List<SystemUser> allUser = systemUserService.queryAllUser();
		int totalRecord = allUser.size();

		int totalPage = (totalRecord - 1 + Integer.parseInt(rows)) / Integer.parseInt(rows);
		int index = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("total", totalPage);
		jsonObj.put("page", page);
		jsonObj.put("records", totalRecord);

		JSONArray row = new JSONArray();
		for (int i = index; i < Integer.parseInt(rows) + index && i < totalRecord; i++) {
			JSONObject cell = new JSONObject();
			cell.put("id", allUser.get(i).getId());
			cell.put("loginName", allUser.get(i).getLoginName());
			cell.put("accountStatus", allUser.get(i).getAccountStatus());

			JSONArray roles = new JSONArray();
			for (int j = 0; j < allUser.get(i).getSystemRoles().size(); j++) {
				roles.add(allUser.get(i).getSystemRoles().get(j).getRoleName());
			}
			cell.put("systemRoles", roles);

			row.add(cell);
		}
		jsonObj.put("rows", row);

		return jsonObj;

	}

	// 修改管理员角色
	@ResponseBody
	@RequestMapping(value = "/modifyRole", method = RequestMethod.GET)
	public Map<String, Object> modifyRole(HttpServletRequest request) {

		int userid = Integer.parseInt(request.getParameter("userid"));
		String[] roleId = request.getParameterValues("roleId");
		String[] systemRoles = request.getParameterValues("systemRoles");

		systemUserRoleService.modifyManagerRole(userid, roleId, systemRoles);

		Map<String, Object> map = new HashMap<>();
		map.put("message", "success");

		return map;

	}

	//删除用户
	@ResponseBody
	@RequestMapping(value = "/deleteSystemUser")
	public Map<String, Object> deleteSystemUser(String userid) {
		Map<String,Object> map = new HashMap<>();
		Integer result = systemUserService.deleteSystemUser(Integer.parseInt(userid));
		if(result == 0){
			map.put("message", "failed");
			return map;
		}
		map.put("message", "success");
		return map;
	}
}
