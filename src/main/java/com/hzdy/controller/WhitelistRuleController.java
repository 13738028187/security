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

import com.hzdy.entity.WhitelistRuleCIP;
import com.hzdy.entity.WhitelistRuleICMP;
import com.hzdy.entity.WhitelistRuleMODBUS;
import com.hzdy.entity.WhitelistRuleOPC;
import com.hzdy.entity.WhitelistRuleS7;
import com.hzdy.entity.WhitelistRuleTCP;
import com.hzdy.entity.WhitelistRuleUDP;
import com.hzdy.service.WhitelistRuleService;
import com.hzdy.utils.PageUtils;
import com.hzdy.utils.R;

@Controller
@RequestMapping("whitelistRule")
public class WhitelistRuleController {

	@Resource
	private WhitelistRuleService whitelistRuleService;
	
	@ResponseBody
	@RequestMapping(value = "/listOPCrules")
	public R listOPCrules(String ruleName, Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("ruleName", ruleName);

		List<WhitelistRuleOPC> opcs = whitelistRuleService.queryOPCwhitelist(map);
		Integer total = whitelistRuleService.queryOPCtotal(map);
		PageUtils pageUtil = new PageUtils(opcs, total, limit, page);

		return R.ok().put("page", pageUtil);
	}

	@ResponseBody
	@RequestMapping(value = "/addOPCrule", produces = "application/json;charset=utf-8")
	public String addOPCrule(@ModelAttribute WhitelistRuleOPC opcRule) {
		return whitelistRuleService.insertOPCwhitelist(opcRule);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteOPCrules", produces = "application/json;charset=utf-8")
	public String deleteOPCrules(HttpServletRequest request) {
		String[] whitelistRuleOPCIds = request.getParameter("opcRuleIds").split(";");
		return whitelistRuleService.deleteOPCwhitelist(whitelistRuleOPCIds);
	}

	@ResponseBody
	@RequestMapping(value = "/queryOPCwhitelistRuleById")
	public WhitelistRuleOPC queryOPCwhitelistRuleById(HttpServletRequest request) {
		String whitelistRuleOPCId = request.getParameter("whitelistRuleOPCId");
		if (whitelistRuleOPCId == null)
			return null;
		else {
			WhitelistRuleOPC opcRule = whitelistRuleService.queryOPCwhitelistById(Integer.parseInt(whitelistRuleOPCId));
			return opcRule;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/modifyOPCrule", produces = "application/json;charset=utf-8")
	public String modifyOPCrule(@ModelAttribute WhitelistRuleOPC opcRule) {
		return whitelistRuleService.updateOPCwhitelist(opcRule);
	}
	
	@ResponseBody
	@RequestMapping(value = "/listS7rules")
	public R listS7rules(String ruleName, Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("ruleName", ruleName);

		List<WhitelistRuleS7> s7s = whitelistRuleService.queryS7whitelist(map);
		Integer total = whitelistRuleService.queryS7total(map);
		PageUtils pageUtil = new PageUtils(s7s, total, limit, page);

		return R.ok().put("page", pageUtil);
	}

	@ResponseBody
	@RequestMapping(value = "/addS7rule", produces = "application/json;charset=utf-8")
	public String addS7rule(@ModelAttribute WhitelistRuleS7 s7Rule) {
		return whitelistRuleService.insertS7whitelist(s7Rule);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteS7rules", produces = "application/json;charset=utf-8")
	public String deleteS7rules(HttpServletRequest request) {
		String[] whitelistRuleS7Ids = request.getParameter("s7RuleIds").split(";");
		return whitelistRuleService.deleteS7whitelist(whitelistRuleS7Ids);
	}

	@ResponseBody
	@RequestMapping(value = "/queryS7whitelistRuleById")
	public WhitelistRuleS7 queryS7whitelistRuleById(HttpServletRequest request) {
		String whitelistRuleS7Id = request.getParameter("whitelistRuleS7Id");
		if (whitelistRuleS7Id == null)
			return null;
		else {
			WhitelistRuleS7 s7Rule = whitelistRuleService.queryS7whitelistById(Integer.parseInt(whitelistRuleS7Id));
			return s7Rule;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/modifyS7rule", produces = "application/json;charset=utf-8")
	public String modifyS7rule(@ModelAttribute WhitelistRuleS7 s7Rule) {
		return whitelistRuleService.updateS7whitelist(s7Rule);
	}
	
	@ResponseBody
	@RequestMapping(value = "/listMODBUSrules")
	public R listMODBUSrules(String ruleName, Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("ruleName", ruleName);

		List<WhitelistRuleMODBUS> modbuss = whitelistRuleService.queryMODBUSwhitelist(map);
		Integer total = whitelistRuleService.queryMODBUStotal(map);
		PageUtils pageUtil = new PageUtils(modbuss, total, limit, page);

		return R.ok().put("page", pageUtil);
	}

	@ResponseBody
	@RequestMapping(value = "/addMODBUSrule", produces = "application/json;charset=utf-8")
	public String addMODBUSrule(@ModelAttribute WhitelistRuleMODBUS modbusRule) {
		return whitelistRuleService.insertMODBUSwhitelist(modbusRule);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteMODBUSrules", produces = "application/json;charset=utf-8")
	public String deleteMODBUSrules(HttpServletRequest request) {
		String[] whitelistRuleMODBUSIds = request.getParameter("modbusRuleIds").split(";");
		return whitelistRuleService.deleteMODBUSwhitelist(whitelistRuleMODBUSIds);
	}

	@ResponseBody
	@RequestMapping(value = "/queryMODBUSwhitelistRuleById")
	public WhitelistRuleMODBUS queryMODBUSwhitelistRuleById(HttpServletRequest request) {
		String whitelistRuleMODBUSId = request.getParameter("whitelistRuleMODBUSId");
		if (whitelistRuleMODBUSId == null)
			return null;
		else {
			WhitelistRuleMODBUS modbusRule = whitelistRuleService.queryMODBUSwhitelistById(Integer.parseInt(whitelistRuleMODBUSId));
			return modbusRule;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/modifyMODBUSrule", produces = "application/json;charset=utf-8")
	public String modifyMODBUSrule(@ModelAttribute WhitelistRuleMODBUS modbusRule) {
		return whitelistRuleService.updateMODBUSwhitelist(modbusRule);
	}
	
	@ResponseBody
	@RequestMapping(value = "/listCIPrules")
	public R listCIPrules(String ruleName, Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("ruleName", ruleName);

		List<WhitelistRuleCIP> cips = whitelistRuleService.queryCIPwhitelist(map);
		Integer total = whitelistRuleService.queryCIPtotal(map);
		PageUtils pageUtil = new PageUtils(cips, total, limit, page);

		return R.ok().put("page", pageUtil);
	}

	@ResponseBody
	@RequestMapping(value = "/addCIPrule", produces = "application/json;charset=utf-8")
	public String addCIPrule(@ModelAttribute WhitelistRuleCIP cipRule) {
		return whitelistRuleService.insertCIPwhitelist(cipRule);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteCIPrules", produces = "application/json;charset=utf-8")
	public String deleteCIPrules(HttpServletRequest request) {
		String[] whitelistRuleCIPIds = request.getParameter("cipRuleIds").split(";");
		return whitelistRuleService.deleteCIPwhitelist(whitelistRuleCIPIds);
	}

	@ResponseBody
	@RequestMapping(value = "/queryCIPwhitelistRuleById")
	public WhitelistRuleCIP queryCIPwhitelistRuleById(HttpServletRequest request) {
		String whitelistRuleCIPId = request.getParameter("whitelistRuleCIPId");
		if (whitelistRuleCIPId == null)
			return null;
		else {
			WhitelistRuleCIP cipRule = whitelistRuleService.queryCIPwhitelistById(Integer.parseInt(whitelistRuleCIPId));
			return cipRule;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/modifyCIPrule", produces = "application/json;charset=utf-8")
	public String modifyCIPrule(@ModelAttribute WhitelistRuleCIP cipRule) {
		return whitelistRuleService.updateCIPwhitelist(cipRule);
	}
	
	@ResponseBody
	@RequestMapping(value = "/listICMPrules")
	public R listICMPrules(String ruleName, Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("ruleName", ruleName);

		List<WhitelistRuleICMP> icmps = whitelistRuleService.queryICMPwhitelist(map);
		Integer total = whitelistRuleService.queryICMPtotal(map);
		PageUtils pageUtil = new PageUtils(icmps, total, limit, page);

		return R.ok().put("page", pageUtil);
	}

	@ResponseBody
	@RequestMapping(value = "/addICMPrule", produces = "application/json;charset=utf-8")
	public String addICMPrule(@ModelAttribute WhitelistRuleICMP icmpRule) {
		return whitelistRuleService.insertICMPwhitelist(icmpRule);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteICMPrules", produces = "application/json;charset=utf-8")
	public String deleteICMPrules(HttpServletRequest request) {
		String[] whitelistRuleICMPIds = request.getParameter("icmpRuleIds").split(";");
		return whitelistRuleService.deleteICMPwhitelist(whitelistRuleICMPIds);
	}

	@ResponseBody
	@RequestMapping(value = "/queryICMPwhitelistRuleById")
	public WhitelistRuleICMP queryICMPwhitelistRuleById(HttpServletRequest request) {
		String whitelistRuleICMPId = request.getParameter("whitelistRuleICMPId");
		if (whitelistRuleICMPId == null)
			return null;
		else {
			WhitelistRuleICMP icmpRule = whitelistRuleService.queryICMPwhitelistById(Integer.parseInt(whitelistRuleICMPId));
			return icmpRule;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/modifyICMPrule", produces = "application/json;charset=utf-8")
	public String modifyICMPrule(@ModelAttribute WhitelistRuleICMP icmpRule) {
		return whitelistRuleService.updateICMPwhitelist(icmpRule);
	}
	
	@ResponseBody
	@RequestMapping(value = "/executeICMPrule", produces = "application/json;charset=utf-8")
	public String executeICMPrule(HttpServletRequest request) {
		String whitelistRuleICMPId = request.getParameter("whitelistRuleICMPId");
		if (whitelistRuleICMPId == null)
			return "无法获取该规则信息";
		else {
			WhitelistRuleICMP icmpRule = whitelistRuleService.queryICMPwhitelistById(Integer.parseInt(whitelistRuleICMPId));
			return whitelistRuleService.executeICMPrule(icmpRule);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/listTCPrules")
	public R listTCPrules(String ruleName, Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("ruleName", ruleName);

		List<WhitelistRuleTCP> tcps = whitelistRuleService.queryTCPwhitelist(map);
		Integer total = whitelistRuleService.queryTCPtotal(map);
		PageUtils pageUtil = new PageUtils(tcps, total, limit, page);

		return R.ok().put("page", pageUtil);
	}

	@ResponseBody
	@RequestMapping(value = "/addTCPrule", produces = "application/json;charset=utf-8")
	public String addTCPrule(@ModelAttribute WhitelistRuleTCP tcpRule) {
		return whitelistRuleService.insertTCPwhitelist(tcpRule);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteTCPrules", produces = "application/json;charset=utf-8")
	public String deleteTCPrules(HttpServletRequest request) {
		String[] whitelistRuleTCPIds = request.getParameter("tcpRuleIds").split(";");
		return whitelistRuleService.deleteTCPwhitelist(whitelistRuleTCPIds);
	}

	@ResponseBody
	@RequestMapping(value = "/queryTCPwhitelistRuleById")
	public WhitelistRuleTCP queryTCPwhitelistRuleById(HttpServletRequest request) {
		String whitelistRuleTCPId = request.getParameter("whitelistRuleTCPId");
		if (whitelistRuleTCPId == null)
			return null;
		else {
			WhitelistRuleTCP tcpRule = whitelistRuleService.queryTCPwhitelistById(Integer.parseInt(whitelistRuleTCPId));
			return tcpRule;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/modifyTCPrule", produces = "application/json;charset=utf-8")
	public String modifyTCPrule(@ModelAttribute WhitelistRuleTCP tcpRule) {
		return whitelistRuleService.updateTCPwhitelist(tcpRule);
	}
	
	@ResponseBody
	@RequestMapping(value = "/executeTCPrule", produces = "application/json;charset=utf-8")
	public String executeTCPrule(HttpServletRequest request) {
		String whitelistRuleTCPId = request.getParameter("whitelistRuleTCPId");
		if (whitelistRuleTCPId == null)
			return "无法获取该规则信息";
		else {
			WhitelistRuleTCP tcpRule = whitelistRuleService.queryTCPwhitelistById(Integer.parseInt(whitelistRuleTCPId));
			return whitelistRuleService.executeTCPrule(tcpRule);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/listUDPrules")
	public R listUDPrules(String ruleName, Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("ruleName", ruleName);

		List<WhitelistRuleUDP> udps = whitelistRuleService.queryUDPwhitelist(map);
		Integer total = whitelistRuleService.queryUDPtotal(map);
		PageUtils pageUtil = new PageUtils(udps, total, limit, page);

		return R.ok().put("page", pageUtil);
	}

	@ResponseBody
	@RequestMapping(value = "/addUDPrule", produces = "application/json;charset=utf-8")
	public String addUDPrule(@ModelAttribute WhitelistRuleUDP udpRule) {
		return whitelistRuleService.insertUDPwhitelist(udpRule);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteUDPrules", produces = "application/json;charset=utf-8")
	public String deleteUDPrules(HttpServletRequest request) {
		String[] whitelistRuleUDPIds = request.getParameter("udpRuleIds").split(";");
		return whitelistRuleService.deleteUDPwhitelist(whitelistRuleUDPIds);
	}

	@ResponseBody
	@RequestMapping(value = "/queryUDPwhitelistRuleById")
	public WhitelistRuleUDP queryUDPwhitelistRuleById(HttpServletRequest request) {
		String whitelistRuleUDPId = request.getParameter("whitelistRuleUDPId");
		if (whitelistRuleUDPId == null)
			return null;
		else {
			WhitelistRuleUDP udpRule = whitelistRuleService.queryUDPwhitelistById(Integer.parseInt(whitelistRuleUDPId));
			return udpRule;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/modifyUDPrule", produces = "application/json;charset=utf-8")
	public String modifyUDPrule(@ModelAttribute WhitelistRuleUDP udpRule) {
		return whitelistRuleService.updateUDPwhitelist(udpRule);
	}
	
	@ResponseBody
	@RequestMapping(value = "/executeUDPrule", produces = "application/json;charset=utf-8")
	public String executeUDPrule(HttpServletRequest request) {
		String whitelistRuleUDPId = request.getParameter("whitelistRuleUDPId");
		if (whitelistRuleUDPId == null)
			return "无法获取该规则信息";
		else {
			WhitelistRuleUDP udpRule = whitelistRuleService.queryUDPwhitelistById(Integer.parseInt(whitelistRuleUDPId));
			return whitelistRuleService.executeUDPrule(udpRule);
		}
	}

}
