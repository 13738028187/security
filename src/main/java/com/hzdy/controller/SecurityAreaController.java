package com.hzdy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hzdy.entity.Interfaces;
import com.hzdy.entity.SecurityArea;
import com.hzdy.entity.SecurityAreaConfig;
import com.hzdy.entity.SecurityAreaDevice;
import com.hzdy.entity.SecurityAreaInterface;
import com.hzdy.service.InterfacesService;
import com.hzdy.service.SecurityAreaConfigService;
import com.hzdy.service.SecurityAreaDeviceService;
import com.hzdy.service.SecurityAreaInterfaceService;
import com.hzdy.service.SecurityAreaService;
import com.hzdy.utils.PageUtils;
import com.hzdy.utils.R;

/**
 * 安全域
 * 
 * @author zhangjun
 * @date 2017年10月31日 下午4:00:18
 * @version 1.0
 */
@Controller
@RequestMapping("securityArea")
public class SecurityAreaController {

	@Resource
	private SecurityAreaService securityAreaService;
	@Resource
	private SecurityAreaDeviceService securityAreaDeviceService;
	@Resource
	private SecurityAreaInterfaceService securityAreaInterfaceService;
	@Resource
	private InterfacesService interfacesService;
	@Resource
	private SecurityAreaConfigService securityAreaConfigService;

	@RequestMapping("list")
	@ResponseBody
	public R list(SecurityArea SecurityArea, Integer page, Integer limit) {

		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("name", SecurityArea.getName());

		// 查询列表数据
		List<SecurityArea> eList = securityAreaService.queryList(map);
		int total = securityAreaService.queryTotal(map);
		/*
		 * for(SecurityArea sa :eList){ StringBuffer sb=new StringBuffer();
		 * for(Interfaces i:sa.getInterlist()){ sb.append(i.getInterfaceName()+";"); }
		 * sb.deleteCharAt(sb.length()-1); sa.setInclude_interface(sb.toString()); }
		 */
		PageUtils pageUtil = new PageUtils(eList, total, limit, page);
		return R.ok().put("page", pageUtil);

	}

	@RequestMapping("delete")
	@ResponseBody
	public R delete(String ids) {
		String securityAreaIds[] = ids.split(";");
		for (int i = 0; i < securityAreaIds.length; i++) {
			if (securityAreaService.delete(Integer.parseInt(securityAreaIds[i])) != 1) {
				return R.error("删除失败");
			}
			securityAreaConfigService.deleteConfigBySecurityAreaId(Integer.parseInt(securityAreaIds[i]));
		}
		return R.ok();
	}

	@RequestMapping("getSecurityArea")
	@ResponseBody
	public R getSecurityArea(int id) {
		SecurityArea SecurityArea = securityAreaService.querySecurityAreaById(id);
		return R.ok().put("data", SecurityArea);
	}

	@RequestMapping("insertSecurityArea")
	@ResponseBody
	public R insertSecurityArea(SecurityArea securityArea) {
		if (securityArea.getName().equals("")) {
			return R.error("安全域名不能为空");
		}

		if (securityAreaService.insert(securityArea) == 1) {
			return R.ok();
		}
		return R.error("添加失败");
	}

	@RequestMapping("updateSecurityArea")
	@ResponseBody
	public R updateSecurityArea(SecurityArea securityArea) {
		
		if (securityArea.getName().equals("")) {
			return R.error("安全域名不能为空");
		}
		if (securityAreaService.update(securityArea) == 1) {
			return R.ok();
		}
		return R.error("更新失败");
	}

	@RequestMapping("getInterface")
	@ResponseBody
	public R getInterface(int id) {
		List<Interfaces> ilist = interfacesService.queryBySecurityAreaId(id);
		return R.ok().put("list", ilist);
	}

	@ResponseBody
	@RequestMapping(value = "listInterface")
	public R listDevice(Integer securityAreaId, String interfaceName, Integer page, Integer limit) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("securityAreaId", securityAreaId);
		map.put("interfaceName", interfaceName);

		List<SecurityAreaInterface> interfaces = securityAreaInterfaceService.queryList(map);
		Integer total = securityAreaInterfaceService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(interfaces, total, limit, page);

		return R.ok().put("page", pageUtil);

	}

	@ResponseBody
	@RequestMapping(value = "listDevice")
	public String listDevice() {
		Map<String, Object> map = new HashMap<>();
		List<SecurityAreaDevice> devices = securityAreaDeviceService.queryList(map);
		return JSON.toJSONString(devices);
	}

	@ResponseBody
	@RequestMapping(value = "save", produces = "application/json;charset=utf-8")
	public String save(@ModelAttribute SecurityAreaDevice device) {
		if (device == null)
			return "无法获得该设备接口信息";
		else
			return securityAreaService.updateDeviceInterface(device);
	}

	@ResponseBody
	@RequestMapping(value = "addDeviceInterface", produces = "application/json;charset=utf-8")
	public String addDeviceInterface(@ModelAttribute SecurityAreaDevice device) {
		if (device == null)
			return "无法获得输入的设备接口信息";
		else
			return securityAreaService.insertDeviceInterface(device);
	}

	@ResponseBody
	@RequestMapping(value = "deleteDeviceInterfaces", produces = "application/json;charset=utf-8")
	public String deleteDeviceInterfaces(HttpServletRequest request) {
		String[] deviceIds = request.getParameter("deviceIds").split(";");
		return securityAreaService.deleteDeviceInterface(deviceIds);
	}

	@ResponseBody
	@RequestMapping(value = "getInterfaces", produces = "application/json;charset=utf-8")
	public String getInterfaces(Integer deviceId) {
		List<String> interfaces = securityAreaInterfaceService.getInterfaces(deviceId);
		return JSON.toJSONString(interfaces);
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping(value = "addInterface", produces = "application/json;charset=utf-8")
	public String addInterface(String interfaceName, Integer securityAreaId, Integer deviceId) {
		try {
			String[] interfaceNames = interfaceName.split(";");
			for (int i = 0; i < interfaceNames.length; i++) {
				Map<String, Object> map = new HashMap<>();
				map.put("interfaceName", interfaceNames[i]);
				map.put("securityAreaId", securityAreaId);
				map.put("deviceId", deviceId);
				
				securityAreaInterfaceService.updateInterface(map);
			}
			return "添加成功";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "添加失败";
		}
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping(value = "deleteInterface", produces = "application/json;charset=utf-8")
	public String deleteInterface(String interfaceId) {
		try {
			String[] interfaceIds = interfaceId.split(";");
			for (int i = 0; i < interfaceIds.length; i++) {
				securityAreaInterfaceService.updateInterfaceById(Integer.parseInt(interfaceIds[i]));
			}
			return "删除成功";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "删除失败";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/listSecurityAreaConfig")
	public R listSecurityAreaConfig(Integer securityAreaId, String networkNumber, Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("securityAreaId", securityAreaId);
		map.put("networkNumber", networkNumber);

		List<SecurityAreaConfig> configs = securityAreaConfigService.querySecurityAreaConfig(map);
		Integer total = securityAreaConfigService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(configs, total, limit, page);

		return R.ok().put("page", pageUtil);
	}

	@ResponseBody
	@RequestMapping(value = "/addSecurityAreaConfig", produces = "application/json;charset=utf-8")
	public String addSecurityAreaConfig(@ModelAttribute SecurityAreaConfig config) {
		return securityAreaConfigService.insertSecurityAreaConfig(config);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteSecurityAreaConfig", produces = "application/json;charset=utf-8")
	public String deleteSecurityAreaConfig(HttpServletRequest request) {
		String[] configIds = request.getParameter("configIds").split(";");
		return securityAreaConfigService.deleteSecurityAreaConfig(configIds);
	}

	@ResponseBody
	@RequestMapping(value = "/querySecurityAreaConfigById")
	public SecurityAreaConfig querySecurityAreaConfigById(HttpServletRequest request) {
		String configId = request.getParameter("configId");
		if (configId == null)
			return null;
		else {
			SecurityAreaConfig config = securityAreaConfigService.querySecurityConfigById(Integer.parseInt(configId));
			return config;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/modifySecurityAreaConfig", produces = "application/json;charset=utf-8")
	public String modifySecurityAreaConfig(@ModelAttribute SecurityAreaConfig config) {
		return securityAreaConfigService.updateSecurityAreaConfig(config);
	}
	
}
