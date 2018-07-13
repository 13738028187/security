package com.hzdy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzdy.entity.KeyEvents;
import com.hzdy.service.KeyEventsService;
import com.hzdy.utils.R;
import com.hzdy.utils.PageUtils;

/**
 * 关键事件
 * 
 * <p>
 * 文件名:KeyEventsController
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
@RequestMapping("keyEvents")
public class KeyEventsController {

	@Resource
	private KeyEventsService keyEventsService;

	@RequestMapping("list")
	@ResponseBody
	public R list(KeyEvents keyEvents, Integer page, Integer limit) {
		/* String originIp,String goalIp */
		System.out.println(keyEvents);
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("ruleName", keyEvents.getRuleName());
		// 查询列表数据
		List<KeyEvents> eList = keyEventsService.queryList(map);
		int total = keyEventsService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(eList, total, limit, page);
		return R.ok().put("page", pageUtil);

	}

	@RequestMapping("delete")
	@ResponseBody
	public R delete(String ids) {
		if (keyEventsService.delete(ids) != 1) {
			return R.error("删除失败");
		}
		return R.ok();
	}

	@RequestMapping("getKeyEvents")
	@ResponseBody
	public R getKeyEvents(int id) {
		System.out.println(id);
		KeyEvents keyEvents = keyEventsService.queryKeyEventsById(id);
		return R.ok().put("data", keyEvents);
	}

	@RequestMapping("insertKeyEvents")
	@ResponseBody
	public R insertKeyEvents(KeyEvents keyEvents) {
		if (keyEvents.getOriginIp().equals("")) {
			return R.error("源Ip不能为空");
		}
		if (keyEvents.getGoalIp().equals("")) {
			return R.error("目的Ip不能为空");
		}
		if (keyEvents.getOriginIpMask().equals("")) {
			return R.error("源Ip掩码不能为空");
		}
		if (keyEvents.getGoalIpMask().equals("")) {
			return R.error("目的Ip掩码不能为空");
		}
		if (keyEventsService.insert(keyEvents) == 1) {
			return R.ok();
		}
		return R.error("添加失败");
	}

	@RequestMapping("updateKeyEvents")
	@ResponseBody
	public R updateKeyEvents(KeyEvents keyEvents) {
		if (keyEvents.getOriginIp().equals("")) {
			return R.error("源Ip不能为空");
		}
		if (keyEvents.getGoalIp().equals("")) {
			return R.error("目的Ip不能为空");
		}
		if (keyEvents.getOriginIpMask().equals("")) {
			return R.error("源Ip掩码不能为空");
		}
		if (keyEvents.getGoalIpMask().equals("")) {
			return R.error("目的Ip掩码不能为空");
		}
		if (keyEventsService.update(keyEvents) == 1) {
			return R.ok();
		}
		return R.error("更新失败");
	}
}
