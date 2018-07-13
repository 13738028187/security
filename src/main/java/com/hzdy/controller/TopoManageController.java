package com.hzdy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hzdy.discovery.FoundationDiscovery;
import com.hzdy.discovery.entity.json.FixNetworkGraph;
import com.hzdy.discovery.entity.json.FixNode;
import com.hzdy.discovery.Ip;
import com.hzdy.discovery.entity.json.Link;
import com.hzdy.discovery.entity.json.NetworkGraph;
import com.hzdy.discovery.entity.json.Node;
import com.hzdy.manager.entity.DeviceActualResources;
import com.hzdy.manager.entity.DeviceStaticResources;
import com.hzdy.manager.utils.DeviceResourcesBuilder;
import com.hzdy.service.IpService;
import com.hzdy.service.TopoService;
import com.hzdy.service.impl.IpServiceImpl;
import com.hzdy.utils.FileUtils;
import com.hzdy.utils.R;
import com.hzdy.utils.ShiroUtils;

@Controller
@RequestMapping("topoManage")
public class TopoManageController {
	@Resource
	private IpServiceImpl ipService;

	@Resource
	private TopoService topoService;
	//获取拓扑
	@RequestMapping("getTopo")
	@ResponseBody
	public R getTopo() {
		//从Mogodb里获取数据
		List<FixNetworkGraph> result = topoService.getFixNetworkGraph("topo");
		//若无数据则返回错误
		if (result.size() < 1) {
			return R.error();
		} else {
			return R.ok().put("networkGraph", result.get(0));
		}
	}
	//从路由获取数据
	@RequestMapping("flushTopo")
	@ResponseBody
	public R flushTopo() {
		Map<String, Object> map = new HashMap<>();
		topoService.remove("topo", map);
		FoundationDiscovery d = new FoundationDiscovery();
		d.netSystemSearch("public");

		NetworkGraph networkGraph = d.getNetworkGraphList().get(0);
		networkGraph.setType("NetworkGraph");
		networkGraph.setLable("Ninux Roma");
		networkGraph.setProtocol("OLSR");
		networkGraph.setRevision("0.6.6.2");

		System.out.println("flushTopo:"+networkGraph);
		//d.showtNetworkRoutesList();
		/*topoService.insert("topo", networkGraph);*/
		return R.ok().put("networkGraph", networkGraph).put("code", 2);

	}

	/**
	 * 增加一个节点
	 * 
	 * @param node
	 *            节点
	 * @return {@link R}
	 */
	@RequestMapping("addNode")
	@ResponseBody
	public R addNode(Node node) {
		topoService.addNode("editTopo", node);
		return R.ok();
	}

	/**
	 * 增加一个链接
	 * 
	 * @param link
	 *            链接
	 * @return {@link R}
	 */
	@RequestMapping("addLink")
	@ResponseBody
	public R addLink(Link link) {
		topoService.addLink("editTopo", link);
		return R.ok();
	}
	//已不用，请使用saveTopo
	@RequestMapping("updateTopo")
	@ResponseBody
	public R updateTopo(String table, @RequestBody NetworkGraph networkGraph) {
		System.out.println(networkGraph);
		System.out.println(table);
		Map<String, Object> map = new HashMap<>();
		String loginName = ShiroUtils.getUserEntity().getLoginName();
		map.put("author", loginName);
		topoService.remove(table, map);
		topoService.insert(table, networkGraph);
		return R.ok();
	}
	//根据表名覆盖数据
	@RequestMapping("saveTopo")
	@ResponseBody
	public R saveTopo(String table,@RequestBody FixNetworkGraph fixNetworkGraph) {
		Map<String, Object> map=new HashMap<>();
		String loginName=ShiroUtils.getUserEntity().getLoginName();
		map.put("author", loginName);
		topoService.removeFixNetworkGraph(table, map);
		fixNetworkGraph.setAuthor(loginName);
		topoService.insertFixNetworkGraph(table, fixNetworkGraph);
		return R.ok();
	}
	//删除节点，已不用
	@RequestMapping("removeNodes")
	@ResponseBody
	public R removeNodes(String table, @RequestBody ArrayList<String> nodes) {
		Map<String, Object> map = new HashMap<>();
		String loginName = ShiroUtils.getUserEntity().getLoginName();
		map.put("author", loginName);
		for (int i = 0; i < nodes.size(); i++) {
			// 1.去mogodb里查找nodes[i]相关的link并删除
			// 2.删除mogodb里的nodes[i]
		}

		return R.ok();
	}

	/**
	 * Method Description gose here
	 * 拓扑编辑页面进入时调用
	 * @param userName
	 *            用户名
	 * @return {@link R}
	 */
	@RequestMapping("editTopo")
	@ResponseBody
	public R editTopo() {
		// 获取用户名
		String loginName = ShiroUtils.getUserEntity().getLoginName();
		// 从用户数据表根据用户名查询topo数据
		Map<String, Object> params = new HashMap<>();
		params.put("author", loginName);

		ArrayList<FixNetworkGraph> result=topoService.findFixListByQuery("editTopo", params);
		//如果因为首次登陆没有数据则从主数据表获取
		if(result.size()==0) {
			ArrayList<FixNetworkGraph> nlist=topoService.getFixNetworkGraph("topo");
			if(nlist.size()==0){
				System.out.println(111);
				return flushTopo();				
			}
			FixNetworkGraph fixNetworkGraph=nlist.get(0);
			//如果主数据表也没有就去自发现路由表数据
			if(fixNetworkGraph==null){
				System.out.println(222);
				return flushTopo();
			}else {
				return R.ok().put("code", 1).put("networkGraph", fixNetworkGraph);
			}

		}
		System.out.println(result.get(0));
		return R.ok().put("networkGraph",result.get(0));
	}

	@RequestMapping("testTopo")
	@ResponseBody
	public R testTopo() {	
		//从Mogodb里获取数据
				List<FixNetworkGraph> result = topoService.getFixNetworkGraph("factoryTopo");
				//若无数据则返回错误
				if (result.size() < 1) {
					FixNetworkGraph fixNetworkGraph=new FixNetworkGraph();
					FixNode node=new FixNode();
					node.setLabel("cloud");
					node.setType("-1");
					node.setX(300);
					node.setY(200);
					ArrayList<FixNode> nodes=new ArrayList<>();
					nodes.add(node);
					fixNetworkGraph.setNodes(nodes);
					return R.ok().put("networkGraph",fixNetworkGraph);
				} else {
					return R.ok().put("networkGraph", result.get(0));
				}
		
	}

	//获取请求用户的用户名
	@RequestMapping("getLoginName")
	@ResponseBody
	public R getLoginName() {
		String loginName = ShiroUtils.getUserEntity().getLoginName();
		System.out.println("--------------" + loginName + "---------------");
		return R.ok().put("loginName", loginName);
	}

}
