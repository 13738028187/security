package com.hzdy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzdy.entity.Rule;
import com.hzdy.service.RuleService;
import com.hzdy.utils.R;
import com.hzdy.utils.PageUtils;

/**
 * 规约配置
 * 
 * <p>
 * 文件名:RuleController
 * </p>
 * <p>
 * 描述:
 * </p>
 * 
 * @author zhangjun
 * @date 2017年10月31日 下午4:00:18
 * @version 1.0
 */
@Controller
@RequestMapping("rule")
public class RuleController {

	@Resource
	private RuleService ruleService;

	@RequestMapping("list")
	@ResponseBody
	public R list(Rule rule, Integer page, Integer limit) {
		System.out.println(rule);
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("ruleName", rule.getRuleName());

		// 查询列表数据
		List<Rule> eList = ruleService.queryList(map);
		int total = ruleService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(eList, total, limit, page);
		return R.ok().put("page", pageUtil);

	}

	@RequestMapping("delete")
	@ResponseBody
	public R delete(String ids) {
		if (ruleService.delete(ids) != 1) {
			return R.error("删除失败");
		}
		return R.ok();
	}

	@RequestMapping("getRule")
	@ResponseBody
	public R getRule(int id) {
		System.out.println(id);
		Rule Rule = ruleService.queryRuleById(id);
		return R.ok().put("data", Rule);
	}

	@RequestMapping("insertRule")
	@ResponseBody
	public R insertRule(Rule Rule) {
		if (Rule.getOriginIp().equals("")) {
			return R.error("源Ip不能为空");
		}
		if (Rule.getGoalIp().equals("")) {
			return R.error("目的Ip不能为空");
		}
		if (Rule.getOriginIpMask().equals("")) {
			return R.error("源Ip掩码不能为空");
		}
		if (Rule.getGoalIpMask().equals("")) {
			return R.error("目的Ip掩码不能为空");
		}
		if (Rule.getProtocol().equals("")) {
			return R.error("协议不能为空");
		}
		if (ruleService.insert(Rule) == 1) {
			return R.ok();
		}
		return R.error("添加失败");
	}

	@RequestMapping("updateRule")
	@ResponseBody
	public R updateRule(Rule Rule) {
		if (Rule.getOriginIp().equals("")) {
			return R.error("源Ip不能为空");
		}
		if (Rule.getGoalIp().equals("")) {
			return R.error("目的Ip不能为空");
		}
		if (Rule.getOriginIpMask().equals("")) {
			return R.error("源Ip掩码不能为空");
		}
		if (Rule.getGoalIpMask().equals("")) {
			return R.error("目的Ip掩码不能为空");
		}
		if (Rule.getProtocol().equals("")) {
			return R.error("协议不能为空");
		}
		if (ruleService.update(Rule) == 1) {
			return R.ok();
		}
		return R.error("更新失败");
	}
}
