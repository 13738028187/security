package com.hzdy.mongo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hzdy.discovery.entity.json.Link;
import com.hzdy.mongo.dao.LinkDao;

/**
 * 
 * what
 * 
 * @author kirohuji
 * @version 0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-test-config-mongodb.xml" })
public class TestLink {
	@Resource
	private LinkDao linkDaoImpl;
	private String collectionName;

	@Before
	public void initSpring() {
		collectionName = "links";
	}

	@Test
	public void testAdd() {

		// 添加一百个Link
		for (int i = 0; i < 100; i++) {
			Link Link = new Link("172.17.0.1", "10.0.0."+i);
			linkDaoImpl.insert(Link, collectionName);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("target", "10.0.0.3");
		List<Link> list = linkDaoImpl.findAll(params, collectionName);
		System.out.println("Link.count()==" + list.size());
	}
}
