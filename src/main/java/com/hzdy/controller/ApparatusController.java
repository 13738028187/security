package com.hzdy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hzdy.entity.Apparatus;
import com.hzdy.entity.Priority;
import com.hzdy.service.ApparatusService;
import com.hzdy.utils.PageUtils;
import com.hzdy.utils.R;

@RestController
@RequestMapping("/apparatus")
public class ApparatusController {
	@Resource
	public ApparatusService apparatusService;

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody Apparatus apparatus) {
		apparatusService.save(apparatus);
		return R.ok();
	}

	@RequestMapping("/update")
	public R update(@RequestBody Apparatus apparatus) {
		apparatusService.update(apparatus);
		return R.ok();
	}

	@RequestMapping("/delete/{id}")
	public R delete(@PathVariable("id") int id) {
		apparatusService.delete(id);
		return R.ok();
	}

	@RequestMapping("/find/{id}")
	R queryObject(@PathVariable("id") int id) {
		return R.ok().put("apparatus", apparatusService.queryObject(id));
	}

	R queryTotal() {
		return R.ok().put("total", apparatusService.queryTotal());
	}

	ArrayList<Apparatus> queryAll() {
		return apparatusService.queryAll();
	}

	@RequestMapping("/list")
	public R list(Integer page, @RequestParam(value = "limit",defaultValue = "10")Integer limit, Integer priorityType) {

		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		if (priorityType != null) {
			map.put("priorityType", priorityType == -1 ? null : priorityType);
		}

		// 查询列表数据
		List<Apparatus> menuList = apparatusService.queryList(map);
		int total = apparatusService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(menuList, total, limit, page);

		return R.ok().put("page", pageUtil);
	}
}
