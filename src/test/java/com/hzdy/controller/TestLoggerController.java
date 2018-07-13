package com.hzdy.controller;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * 
 * 测试SecurityManagerController类
 * 
 * @author kirohuji
 * @version 0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-config-mvc.xml", "classpath*:spring-config-cache.xml",
		"classpath*:spring-test-config-jdbc.xml", "classpath*:classpath:spring-config-shiro.xml",
		"classpath*:spring-config-task.xml", "classpath*:spring-config-redis.xml", "classpath*:spring-test-config-mongodb.xml" })
public class TestLoggerController {
	private MockMvc mockMvc;
	@Resource
	private LoggerController loggerController;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(loggerController).build();
	}

	
	@Test
	public void testSyslog() throws Exception {
		String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/logger/syslog")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)// 数据的格式
				// 添加参数(可以添加多个)
		) // 打印出请求和相应的内容
				.andReturn().getResponse().getContentAsString(); // 将相应的数据转换为字符串
		System.out.println("-----返回的json = " + responseString);
	}
}
