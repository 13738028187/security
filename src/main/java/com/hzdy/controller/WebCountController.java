package com.hzdy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzdy.entity.WebCount;
import com.hzdy.service.WebCountService;
import com.hzdy.utils.R;
import com.hzdy.utils.PageUtils;

/**
 * 规约配置
 * 
 * <p>
 * 文件名:WebCountController
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
@RequestMapping("webCount")
public class WebCountController {

	@Resource
	private WebCountService webCountService;

	@RequestMapping("list")
	@ResponseBody
	public R list(WebCount WebCount, Integer page, Integer limit) {
		System.out.println(WebCount);
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("ruleName", WebCount.getRuleName());

		// 查询列表数据
		List<WebCount> eList = webCountService.queryList(map);
		int total = webCountService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(eList, total, limit, page);
		return R.ok().put("page", pageUtil);

	}

	@RequestMapping("delete")
	@ResponseBody
	public R delete(String ids) {
		if (webCountService.delete(ids) != 1) {
			return R.error("删除失败");
		}
		return R.ok();
	}

	@RequestMapping("getWebCount")
	@ResponseBody
	public R getWebCount(int id) {
		System.out.println(id);
		WebCount WebCount = webCountService.queryWebCountById(id);
		return R.ok().put("data", WebCount);
	}

	@RequestMapping("insertWebCount")
	@ResponseBody
	public R insertWebCount(WebCount WebCount) {
		if (WebCount.getOriginIp().equals("")) {
			return R.error("源Ip不能为空");
		}
		if (WebCount.getGoalIp().equals("")) {
			return R.error("目的Ip不能为空");
		}
		if (WebCount.getOriginIpMask().equals("")) {
			return R.error("源Ip掩码不能为空");
		}
		if (WebCount.getGoalIpMask().equals("")) {
			return R.error("目的Ip掩码不能为空");
		}
		if (WebCount.getStartOriginPort().equals("")) {
			return R.error("开始源端口不能为空");
		}
		if (WebCount.getEndOriginPort().equals("")) {
			return R.error("结束源端口不能为空");
		}
		if (WebCount.getStartGoalPort().equals("")) {
			return R.error("开始目的端口不能为空");
		}
		if (WebCount.getEndGoalPort().equals("")) {
			return R.error("结束目的端口不能为空");
		}
		if (WebCount.getProtocol().equals("")) {
			return R.error("协议不能为空");
		}
		if (webCountService.insert(WebCount) == 1) {
			return R.ok();
		}
		return R.error("添加失败");
	}

	@RequestMapping("updateWebCount")
	@ResponseBody
	public R updateWebCount(WebCount WebCount) {
		if (WebCount.getOriginIp().equals("")) {
			return R.error("源Ip不能为空");
		}
		if (WebCount.getGoalIp().equals("")) {
			return R.error("目的Ip不能为空");
		}
		if (WebCount.getOriginIpMask().equals("")) {
			return R.error("源Ip掩码不能为空");
		}
		if (WebCount.getGoalIpMask().equals("")) {
			return R.error("目的Ip掩码不能为空");
		}
		if (WebCount.getProtocol().equals("")) {
			return R.error("协议不能为空");
		}
		if (WebCount.getStartOriginPort().equals("")) {
			return R.error("开始源端口不能为空");
		}
		if (WebCount.getEndOriginPort().equals("")) {
			return R.error("结束源端口不能为空");
		}
		if (WebCount.getStartGoalPort().equals("")) {
			return R.error("开始目的端口不能为空");
		}
		if (WebCount.getEndGoalPort().equals("")) {
			return R.error("结束目的端口不能为空");
		}
		if (webCountService.update(WebCount) == 1) {
			return R.ok();
		}
		return R.error("更新失败");
	}
}
