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

import com.hzdy.discovery.entity.json.Node;
import com.hzdy.mongo.dao.NodeDao;

/**
 * 
 * what
 * 
 * @author kirohuji
 * @version 0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-test-config-mongodb.xml" })
public class TestNode {
	@Resource
	private NodeDao NodeDaoImpl;
	private String collectionName;

	@Before
	public void initSpring() {
		collectionName = "nodes";
	}

	@Test
	public void testAdd() {

		// 添加一百个Node
		for (int i = 0; i < 100; i++) {
			Node node = new Node("172.17.0.1", "10.0.0."+i);
			node.setType("123");
			NodeDaoImpl.insert(node, collectionName);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("label", "10.0.0.3");
		List<Node> list = NodeDaoImpl.findAll(params, collectionName);
		System.out.println("Node.count()==" + list.size());
	}
}
