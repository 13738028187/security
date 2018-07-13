package com.hzdy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzdy.entity.UserDefinedMODBUS;
import com.hzdy.entity.UserDefinedOPC;
import com.hzdy.entity.UserDefinedS7;
import com.hzdy.service.UserDefinedService;
import com.hzdy.utils.PageUtils;
import com.hzdy.utils.R;

@Controller
@RequestMapping(value = "/userDefined")
public class UserDefinedController {

	@Resource
	private UserDefinedService userDefinedService;

	@ResponseBody
	@RequestMapping(value = "/listOPCrules")
	public R listOPCrules(String ruleName, Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("ruleName", ruleName);

		List<UserDefinedOPC> opcs = userDefinedService.queryOPCuserDefined(map);
		Integer total = userDefinedService.queryOPCtotal(map);
		PageUtils pageUtil = new PageUtils(opcs, total, limit, page);

		return R.ok().put("page", pageUtil);
	}

	@ResponseBody
	@RequestMapping(value = "/addOPCrule", produces = "application/json;charset=utf-8")
	public String addOPCrule(@ModelAttribute UserDefinedOPC opcRule) {
		return userDefinedService.insertOPCuserDefined(opcRule);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteOPCrules", produces = "application/json;charset=utf-8")
	public String deleteOPCrules(HttpServletRequest request) {
		String[] userDefinedOPCIds = request.getParameter("opcRuleIds").split(";");
		return userDefinedService.deleteOPCuserDefined(userDefinedOPCIds);
	}

	@ResponseBody
	@RequestMapping(value = "/queryOPCuserDefinedById")
	public UserDefinedOPC queryOPCuserDefinedById(HttpServletRequest request) {
		String userDefinedOPCId = request.getParameter("userDefinedOPCId");
		if (userDefinedOPCId == null)
			return null;
		else {
			UserDefinedOPC opcRule = userDefinedService.queryOPCuserDefinedById(Integer.parseInt(userDefinedOPCId));
			return opcRule;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/modifyOPCrule", produces = "application/json;charset=utf-8")
	public String modifyOPCrule(@ModelAttribute UserDefinedOPC opcRule) {
		return userDefinedService.updateOPCuserDefined(opcRule);
	}
	
	@ResponseBody
	@RequestMapping(value = "/listS7rules")
	public R listS7rules(String ruleName, Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("ruleName", ruleName);

		List<UserDefinedS7> s7s = userDefinedService.queryS7userDefined(map);
		Integer total = userDefinedService.queryS7total(map);
		PageUtils pageUtil = new PageUtils(s7s, total, limit, page);

		return R.ok().put("page", pageUtil);
	}

	@ResponseBody
	@RequestMapping(value = "/addS7rule", produces = "application/json;charset=utf-8")
	public String addS7rule(@ModelAttribute UserDefinedS7 s7Rule) {
		return userDefinedService.insertS7userDefined(s7Rule);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteS7rules", produces = "application/json;charset=utf-8")
	public String deleteS7rules(HttpServletRequest request) {
		String[] userDefinedS7Ids = request.getParameter("s7RuleIds").split(";");
		return userDefinedService.deleteS7userDefined(userDefinedS7Ids);
	}

	@ResponseBody
	@RequestMapping(value = "/queryS7userDefinedById")
	public UserDefinedS7 queryS7userDefinedById(HttpServletRequest request) {
		String userDefinedS7Id = request.getParameter("userDefinedS7Id");
		if (userDefinedS7Id == null)
			return null;
		else {
			UserDefinedS7 s7Rule = userDefinedService.queryS7userDefinedById(Integer.parseInt(userDefinedS7Id));
			return s7Rule;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/modifyS7rule", produces = "application/json;charset=utf-8")
	public String modifyS7rule(@ModelAttribute UserDefinedS7 s7Rule) {
		return userDefinedService.updateS7userDefined(s7Rule);
	}
	
	@ResponseBody
	@RequestMapping(value = "/listMODBUSrules")
	public R listMODBUSrules(String ruleName, Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("ruleName", ruleName);

		List<UserDefinedMODBUS> modbuss = userDefinedService.queryMODBUSuserDefined(map);
		Integer total = userDefinedService.queryMODBUStotal(map);
		PageUtils pageUtil = new PageUtils(modbuss, total, limit, page);

		return R.ok().put("page", pageUtil);
	}

	@ResponseBody
	@RequestMapping(value = "/addMODBUSrule", produces = "application/json;charset=utf-8")
	public String addMODBUSrule(@ModelAttribute UserDefinedMODBUS modbusRule) {
		return userDefinedService.insertMODBUSuserDefined(modbusRule);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteMODBUSrules", produces = "application/json;charset=utf-8")
	public String deleteMODBUSrules(HttpServletRequest request) {
		String[] userDefinedMODBUSIds = request.getParameter("modbusRuleIds").split(";");
		return userDefinedService.deleteMODBUSuserDefined(userDefinedMODBUSIds);
	}

	@ResponseBody
	@RequestMapping(value = "/queryMODBUSuserDefinedById")
	public UserDefinedMODBUS queryMODBUSuserDefinedById(HttpServletRequest request) {
		String userDefinedMODBUSId = request.getParameter("userDefinedMODBUSId");
		if (userDefinedMODBUSId == null)
			return null;
		else {
			UserDefinedMODBUS modbusRule = userDefinedService.queryMODBUSuserDefinedById(Integer.parseInt(userDefinedMODBUSId));
			return modbusRule;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/modifyMODBUSrule", produces = "application/json;charset=utf-8")
	public String modifyMODBUSrule(@ModelAttribute UserDefinedMODBUS modbusRule) {
		return userDefinedService.updateMODBUSuserDefined(modbusRule);
	}

}
