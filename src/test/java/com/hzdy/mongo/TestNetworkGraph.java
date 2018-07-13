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

import com.hzdy.discovery.FoundationDiscovery;
import com.hzdy.discovery.entity.json.NetworkGraph;
import com.hzdy.mongo.dao.NetworkGraphDao;

/**
 * 
 * what
 * 
 * @author kirohuji
 * @version 0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-config-mongodb.xml" })
public class TestNetworkGraph {
	@Resource
	private NetworkGraphDao networkGraphDaoImpl;
	private String collectionName;

	@Before
	public void initSpring() {
		collectionName = "topo";
	}

	@Test
	public void testAdd() {
		/*FoundationDiscovery d = new FoundationDiscovery();
		d.netSystemSearch("public");
		d.getNetworkGraphList().stream().forEach(n->{
			networkGraphDaoImpl.insert(n, collectionName);
		});*/
		Map<String, Object> params = new HashMap<String, Object>();
		networkGraphDaoImpl.remove(params, collectionName);
		
	}
}
