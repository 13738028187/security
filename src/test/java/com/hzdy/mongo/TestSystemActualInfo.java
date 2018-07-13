package com.hzdy.mongo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hzdy.hardware.entity.SystemActualInfo;
import com.hzdy.hardware.entity.SystemActualInfoFactory;
import com.hzdy.mongo.dao.SystemActualInfoDao;
import com.hzdy.service.impl.SystemActualInfoService;

/**
* 
* what
* 
* @author kirohuji
* @version 0.1
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-test-config.xml" })
public class TestSystemActualInfo {
	@Resource
	private SystemActualInfoService systemActualInfoService;
	private String collectionName;

	@Before
	public void initSpring() {
		collectionName = "system_actual_info";
	}

	@Test
	public void testRun() {
		SystemActualInfo systemActualInfo = new SystemActualInfo();
		SystemActualInfoFactory.getInstance().build(systemActualInfo);
		systemActualInfoService.insert(systemActualInfo, "system_actual_info");
/*		List<String> result=new ArrayList<>();
		Map<String, Object> params = new HashMap<String, Object>();
	    systemActualInfoDaoImpl.findAll(params, "system_actual_info").stream().forEach(s->{
			result.add(JSON.toJSONString(s));
		});
		System.out.println(result);*/
	}
}
