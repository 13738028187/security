package com.hzdy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hzdy.discovery.entity.json.FixNetworkGraph;
import com.hzdy.discovery.entity.json.Link;
import com.hzdy.discovery.entity.json.NetworkGraph;
import com.hzdy.discovery.entity.json.Node;
import com.hzdy.manager.entity.DeviceActualResources;
import com.hzdy.manager.entity.DeviceStaticResources;
import com.hzdy.mongo.dao.DeviceActualResourcesDao;
import com.hzdy.mongo.dao.DeviceStaticResourcesDao;
import com.hzdy.mongo.dao.FixNetworkGraphDao;
import com.hzdy.mongo.dao.NetworkGraphDao;

/**
 * 
 * 注意这个类服务是基于Mongodb的 这个类是用来封装Topo图的增删改查
 * 
 * @author kirohuji
 * @version 0.1
 */
@Service
public class TopoService {
	@Resource
	private NetworkGraphDao networkGraphDaoImpl;
	@Resource
	private FixNetworkGraphDao fixNetworkGraphDaoImpl;
	@Resource
	private DeviceActualResourcesDao deviceActualResourcesDaoImpl;
	@Resource
	private DeviceStaticResourcesDao deviceStaticResourcesDaoImpl;

	public void insert(String collectionName, NetworkGraph n) {
		networkGraphDaoImpl.insert(n, collectionName);
	}
	public void insertFixNetworkGraph(String collectionName, FixNetworkGraph fn) {
		fixNetworkGraphDaoImpl.insert(fn, collectionName);
	}
	public void removeFixNetworkGraph(String collectionName,Map<String, Object> map) {
		
		fixNetworkGraphDaoImpl.remove(map, collectionName);
	}

	public void addNode(String collectionName, Node node) {
		networkGraphDaoImpl.<Node>push(collectionName, "ndoes", node);
	}

	public void addLink(String collectionName, Link link) {
		networkGraphDaoImpl.<Link>push(collectionName, "links", link);
	}

	public void remove(String collectionName,Map<String, Object> map) {
		
		networkGraphDaoImpl.remove(map, collectionName);
	}

	public void addAuthor(String collectionName, String username) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		networkGraphDaoImpl.update(params, collectionName, "username", username);
	}

	public ArrayList<Link> getLinks(String collectionName) {
		Map<String, Object> params = new HashMap<String, Object>();
		ArrayList<Link> lists = new ArrayList<>();
		ArrayList<NetworkGraph> result = (ArrayList<NetworkGraph>) networkGraphDaoImpl.findAll(params, collectionName,
				"links");
		result.stream().forEach(n -> {
			lists.addAll(n.getLinks());
		});
		return lists;
	}

	public ArrayList<Node> getNodes(String collectionName) {
		Map<String, Object> params = new HashMap<String, Object>();
		ArrayList<Node> lists = new ArrayList<>();
		ArrayList<NetworkGraph> result = (ArrayList<NetworkGraph>) networkGraphDaoImpl.findAll(params, collectionName,
				"nodes");
		result.stream().forEach(n -> {
			lists.addAll(n.getNodes());
		});
		return lists;
	}

	public ArrayList<NetworkGraph> getNetworkGraph(String collectionName){
	Map<String, Object> params = new HashMap<String, Object>();
	return (ArrayList<NetworkGraph>) networkGraphDaoImpl.findAll(params, collectionName);
	}
	public ArrayList<FixNetworkGraph> getFixNetworkGraph(String collectionName){
		Map<String, Object> params = new HashMap<String, Object>();
		return (ArrayList<FixNetworkGraph>) fixNetworkGraphDaoImpl.findAll(params, collectionName);
		}


	public ArrayList<NetworkGraph> findListByQuery(String collectionName,Map<String, Object> params){
		return (ArrayList<NetworkGraph>) networkGraphDaoImpl.findList(params, collectionName);
	}
	public ArrayList<FixNetworkGraph> findFixListByQuery(String collectionName,Map<String, Object> params){
		return (ArrayList<FixNetworkGraph>) fixNetworkGraphDaoImpl.findList(params, collectionName);
	}
	public ArrayList<DeviceActualResources> getDeviceActualResources(String collectionName){
		Map<String, Object> params = new HashMap<String, Object>();
		return (ArrayList<DeviceActualResources>) deviceActualResourcesDaoImpl.findAll(params, collectionName);
	}
	public ArrayList<DeviceStaticResources> getDeviceStaticResources(String collectionName){
		Map<String, Object> params = new HashMap<String, Object>();
		return (ArrayList<DeviceStaticResources>) deviceStaticResourcesDaoImpl.findAll(params, collectionName);
	}
	
}
