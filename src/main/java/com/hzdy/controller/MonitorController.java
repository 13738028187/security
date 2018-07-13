package com.hzdy.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzdy.entity.SystemRole;
import com.hzdy.hardware.entity.SystemActualInfo;
import com.hzdy.hardware.entity.SystemInfo;
import com.hzdy.service.impl.CollectorCenterService;
import com.hzdy.service.impl.SystemInfoServiceImpl;

@Controller
@RequestMapping(value = "/monitor")
public class MonitorController {
	@Resource
	private CollectorCenterService collectorCenterService;
	@ResponseBody
	@RequestMapping(value = "/systemInfo", method = RequestMethod.GET)
	public SystemInfo systemInfo() {
		try {
			return collectorCenterService.querySystemInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/javaInformations")
	public List<String> javaInformations() {
		return collectorCenterService.queryJavaInformations();
	}
	@ResponseBody
	@RequestMapping(value = "/upToDate")
	public SystemActualInfo upToDate() {
		return collectorCenterService.queryUpToDate("system_actual_info");
	}
	@ResponseBody
	@RequestMapping(value = "/systemActualInfo")
	public List<String> systemActualInfo() {
		return collectorCenterService.querySystemActualInfo();
	}
}
