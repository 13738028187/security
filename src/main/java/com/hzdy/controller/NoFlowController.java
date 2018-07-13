package com.hzdy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzdy.entity.NoFlow;
import com.hzdy.service.NoFlowService;
import com.hzdy.utils.R;
import com.hzdy.utils.PageUtils;

/**
 * 无流量配置
 * 
 * <p>
 * 文件名:NoFlowController
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
@RequestMapping("noFlow")
public class NoFlowController {

	@Resource
	private NoFlowService noFlowService;

	@RequestMapping("list")
	@ResponseBody
	public R list(NoFlow noFlow, Integer page, Integer limit) {

		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("ruleName", noFlow.getRuleName());

		// 查询列表数据
		List<NoFlow> eList = noFlowService.queryList(map);
		int total = noFlowService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(eList, total, limit, page);
		return R.ok().put("page", pageUtil);

	}

	@RequestMapping("delete")
	@ResponseBody
	public R delete(String ids) {
		if (noFlowService.delete(ids) != 1) {
			return R.error("删除失败");
		}
		return R.ok();
	}

	@RequestMapping("getNoFlow")
	@ResponseBody
	public R getNoFlow(int id) {
		System.out.println(id);
		NoFlow noFlow = noFlowService.queryNoFlowById(id);
		return R.ok().put("data", noFlow);
	}

	@RequestMapping("insertNoFlow")
	@ResponseBody
	public R insertNoFlow(NoFlow noFlow) {
		System.out.println(noFlow);
		if (noFlow.getOriginIp().equals("")) {
			return R.error("源Ip不能为空");
		}
		if (noFlow.getGoalIp().equals("")) {
			return R.error("目的Ip不能为空");
		}
		if (noFlow.getOriginIpMask().equals("")) {
			return R.error("源Ip掩码不能为空");
		}
		if (noFlow.getGoalIpMask().equals("")) {
			return R.error("目的Ip掩码不能为空");
		}
		if (noFlow.getGoalADSL().equals("")) {
			return R.error("目的端口不能为空");
		}
		if (noFlow.getProtocol().equals("")) {
			return R.error("协议不能为空");
		}
		if (noFlow.getInterval() <= 0) {
			return R.error("请输入大于0的整数");
		}
		if (noFlowService.insert(noFlow) == 1) {
			return R.ok();
		}
		return R.error("添加失败");
	}

	@RequestMapping("updateNoFlow")
	@ResponseBody
	public R updateNoFlow(NoFlow noFlow) {
		System.out.println(noFlow);
		if (noFlow.getOriginIp().equals("")) {
			return R.error("源Ip不能为空");
		}
		if (noFlow.getGoalIp().equals("")) {
			return R.error("目的Ip不能为空");
		}
		if (noFlow.getOriginIpMask().equals("")) {
			return R.error("源Ip掩码不能为空");
		}
		if (noFlow.getGoalIpMask().equals("")) {
			return R.error("目的Ip掩码不能为空");
		}
		if (noFlow.getGoalADSL().equals("")) {
			return R.error("目的端口不能为空");
		}
		if (noFlow.getProtocol().equals("")) {
			return R.error("协议不能为空");
		}
		if (noFlow.getInterval() <= 0) {
			return R.error("请输入大于0的整数");
		}
		if (noFlowService.update(noFlow) == 1) {
			return R.ok();
		}
		return R.error("更新失败");
	}
	
	@ResponseBody
	@RequestMapping(value = "execute", produces = "application/json;charset=utf-8")
	public String execute(HttpServletRequest request) {
		String noFlowId = request.getParameter("noFlowId");
		if (noFlowId == null)
			return "无法获取该规则信息";
		else {
			NoFlow noFlow = noFlowService.queryNoFlowById(Integer.parseInt(noFlowId));
			String result = noFlowService.execute(noFlow);
			return result;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "stop", produces = "application/json;charset=utf-8")
	public String stop(HttpServletRequest request) {
		String noFlowId = request.getParameter("noFlowId");
		if (noFlowId == null)
			return "无法获取该规则信息";
		else {
			NoFlow noFlow = noFlowService.queryNoFlowById(Integer.parseInt(noFlowId));
			return noFlowService.stop(noFlow);
		}
	}
}
