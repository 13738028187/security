package com.hzdy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzdy.entity.BaselineDevice;
import com.hzdy.entity.BaselineStrategy;
import com.hzdy.service.BaselineService;

@Controller
public class BaselineController {

	@Resource
	private BaselineService baselineService;

	@ResponseBody
	@RequestMapping(value = "/listStrategies")
	public JSONObject listStrategies(@RequestParam(value = "page", defaultValue = "1") String page,
			@RequestParam(value = "rows", defaultValue = "10") String rows, @RequestParam(value = "name") String name) {
		
		List<BaselineStrategy> strategies = baselineService.queryStrategies(name);
		int totalRecord = strategies.size();

		int totalPage = (totalRecord - 1 + Integer.parseInt(rows)) / Integer.parseInt(rows);
		int index = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("total", totalPage);
		jsonObj.put("page", page);
		jsonObj.put("records", totalRecord);

		JSONArray row = new JSONArray();
		for (int i = index; i < Integer.parseInt(rows) + index && i < totalRecord; i++) {
			JSONObject cell = new JSONObject();
			cell.put("strategyId", strategies.get(i).getStrategyId());
			cell.put("name", strategies.get(i).getName());
			cell.put("type", strategies.get(i).getType());
			cell.put("ips", strategies.get(i).getIps());
			cell.put("ipStatus", strategies.get(i).getIpStatus());
			cell.put("ports", strategies.get(i).getPorts());
			cell.put("portStatus", strategies.get(i).getPortStatus());

			row.add(cell);
		}
		jsonObj.put("rows", row);

		return jsonObj;

	}

	@ResponseBody
	@RequestMapping(value = "/addStrategy", produces = "application/json;charset=utf-8")
	public String addStrategy(@ModelAttribute BaselineStrategy strategy) {

		if (strategy == null)
			return "添加策略失败";
		else
			return baselineService.insertStrategy(strategy);

	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteStrategies", produces = "application/json;charset=utf-8")
	public String deleteStrategies(HttpServletRequest request) {
		
		String[] strategyIds = request.getParameter("strategyIds").split(";");
		return baselineService.deleteStrategy(strategyIds);
		
	}

	@ResponseBody
	@RequestMapping(value = "/modifyStrategy", produces = "application/json;charset=utf-8")
	public String modifyStrategy(@ModelAttribute BaselineStrategy strategy) {

		if (strategy == null)
			return "添加策略失败";
		else
			return baselineService.updateStrategy(strategy);

	}

	@ResponseBody
	@RequestMapping(value = "/showDetails")
	public Map<String, Object> showDetails(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();
		String strategyId = request.getParameter("strategyId");
		if (strategyId == null || "".equals(strategyId)) {
			map.put("message", "无法获得该策略信息");
			return map;
		}

		BaselineStrategy strategy = baselineService.queryStrategyById(Integer.parseInt(strategyId));
		map.put("message", "success");
		map.put("strategy", strategy);
		return map;

	}
	
	@ResponseBody
	@RequestMapping(value = "/listDevices")
	public String listDevices() {
		List<BaselineDevice> devices = baselineService.queryDevices();
		return JSON.toJSONString(devices);
	}
	
	@ResponseBody
	@RequestMapping(value = "/strategyIssued", produces = "application/json;charset=utf-8")
	public String strategyIssued(HttpServletRequest request) {
		String strategyId = request.getParameter("strategyId");
		String[] deviceIds = request.getParameter("deviceIds").split(";");
		if (strategyId == null || "".equals(strategyId))
			return "无法获取策略相关信息";
		return baselineService.strategyIssued(Integer.parseInt(strategyId), deviceIds);
	}

}
