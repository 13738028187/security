package com.hzdy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzdy.discovery.FoundationDiscovery;
import com.hzdy.discovery.Host;
import com.hzdy.discovery.Ip;
import com.hzdy.discovery.entity.json.FixNetworkGraph;
import com.hzdy.discovery.entity.json.NetworkGraph;
import com.hzdy.entity.Equipment;
import com.hzdy.entity.Interfaces;
import com.hzdy.manager.entity.AtEntry;
import com.hzdy.manager.entity.DeviceActualResources;
import com.hzdy.manager.entity.DeviceResources;
import com.hzdy.manager.entity.DeviceStaticResources;
import com.hzdy.manager.entity.IcmpEntry;
import com.hzdy.manager.entity.InterfacesEntry;
import com.hzdy.manager.entity.TcpEntry;
import com.hzdy.manager.entity.TcpProgram;
import com.hzdy.manager.entity.UdpEntry;
import com.hzdy.manager.entity.UdpProgram;
import com.hzdy.mongo.repository.NetworkGraphDaoImpl;
import com.hzdy.service.EquipmentService;
import com.hzdy.service.IpService;
import com.hzdy.service.TopoService;
import com.hzdy.utils.ClassFomaterUtil;
import com.hzdy.utils.PageUtils;
/*import com.hzdy.service.impl.DeviceServiceImpl;*/
import com.hzdy.utils.R;

@Controller
@RequestMapping("equipment")
public class EquipmentController {
	private static final Logger logger = LogManager.getLogger(EquipmentController.class);
	@Resource
	private IpService ipService;
	@Resource
	private TopoService topoService;
	@Resource
	private EquipmentService equipmentService;
	@Resource
	private NetworkGraphDaoImpl networkGraphDao;
	@ResponseBody
	@RequestMapping("/listEquipmentById")
	public Map<String, Object> listEquipmentById(HttpServletRequest request) {

		int id = Integer.parseInt(request.getParameter("id"));

		Map<String, Object> map = new HashMap<>();
		Equipment equipment = equipmentService.queryEquipmentById(id);
		if (equipment != null) {
			map.put("equipment", equipment);
			map.put("message", "success");
		} else {
			map.put("message", "failed");
		}

		return map;

	}

	@ResponseBody
	@RequestMapping("addAllIp")
	public R addAllIp() {
		FoundationDiscovery d = new FoundationDiscovery();
		d.netSystemSearch("public");
		NetworkGraph networkGraph = d.getNetworkGraphList().get(0);
		ArrayList<String> iplist = new ArrayList<String>();
		networkGraph.getNodes().stream().forEach(node -> {
			if (!node.getIp().equals("127.0.0.1")) {
				iplist.add(node.getIp());
			}
		});
		ipService.saveAllIp(iplist);
		return R.ok();
	}

	@ResponseBody
	@RequestMapping("actualEqupment")
	public R actualEqupment(String ip) {
		DeviceActualResources dar = ipService.getDeviceActualResources(ip, "public");
		return R.ok().put("dar", dar);
	}

	@ResponseBody
	@RequestMapping("resources")
	public R resources(Equipment Equipment, Integer page, Integer limit) {
		System.out.println(Equipment);
		ArrayList<DeviceStaticResources> deviceStaticResourcesList = new ArrayList<DeviceStaticResources>();
		ArrayList<Ip> ipArrayList = ipService.queryAll();
		boolean flag= false;
		if((Equipment.getEquipmentName()==null||Equipment.getEquipmentName()=="")&&(Equipment.getIp()==null||Equipment.getIp()=="")&&(Equipment.getWorkPattern()==null||Equipment.getWorkPattern()=="")&&(Equipment.getOnlineStatus()==null)){
			flag=true;
		}
		System.out.println(flag);		
		if (flag==true&&ipArrayList.size() < 1) {
			FoundationDiscovery d = new FoundationDiscovery();
			d.netSystemSearch("public");
			NetworkGraph networkGraph = d.getNetworkGraphList().get(0);
			networkGraph.getNodes().stream().forEach(node -> {
				if (!node.getIp().equals("127.0.0.1")) {
					Ip ip = new Ip(node.getIp());
					ipArrayList.add(ip);
					String oid = UUID.randomUUID().toString().replaceAll("-", "");

					ip.setoId(oid);
					ipService.save(ip);
				}
			});
		}
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("equipmentName", Equipment.getEquipmentName());
		map.put("ip", Equipment.getIp());
		map.put("onlineStatus", Equipment.getOnlineStatus());
		map.put("workPattern", Equipment.getWorkPattern());
		List<Equipment> euipmentList = equipmentService.queryList(map);
		if (flag==true&&euipmentList.size() < 1) {
			ipArrayList.stream().forEach(ip -> {
				DeviceStaticResources dsr = ipService.getDeviceStaticResources(ip.getIp(), "public");
				if (dsr != null) {
					dsr.setId((int) (Math.random() * 1000));
					deviceStaticResourcesList.add(dsr);
					Equipment equipment = new Equipment();
					equipment.setEquipmentName(dsr.getSysName());
					equipment.setoId(ip.getoId());
					equipment.setIp(ip.getIp());
					equipment.setOnlineStatus(1);
					equipment.setOnlineTime("123");
					equipment.setWorkPattern("初始化");
					equipment.setIsDelete(0);
					equipmentService.save(equipment);
					euipmentList.add(equipment);

				}
			});
			deviceStaticResourcesList.stream().forEach(System.out::println);
		}
		int total = equipmentService.queryTotalByMap(map);
		PageUtils pageUtil = new PageUtils(euipmentList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}

	@ResponseBody
	@RequestMapping("getUdpEntry")
	public R getUdpEntry(String ipAddress, String community) {
		DeviceResources dr = new DeviceResources();
		dr.buildUdp(ipAddress, community);
		List<UdpProgram> list=ClassFomaterUtil.updFormater(dr.getUdp());
		int total=list.size();//暂时先这么写
		PageUtils pageUtil = new PageUtils(list, total, 10, 1);
		return R.ok().put("page", pageUtil);
	}
	@ResponseBody
	@RequestMapping("getInterfacesEntry")
	public R getInterfacesEntry(String ipAddress,String community) {
		DeviceResources dr = new DeviceResources();
		dr.buildInterfaces(ipAddress, community);
		if(dr.getInterfaces()==null) {
			List<Interfaces>list=null;
			int total=0;
			PageUtils pageUtil = new PageUtils(list, total, 10, 1);
			return R.ok().put("page", pageUtil);
		}else {
			List<Interfaces>list=ClassFomaterUtil.classFomater(dr.getInterfaces());
			int total=list.size();//暂时先这么写
			PageUtils pageUtil = new PageUtils(list, total, 10, 1);
			return R.ok().put("page", pageUtil);
		}
	}
	@ResponseBody
	@RequestMapping("getLinkInterfacesEntry")
	public R getLinkInterfacesEntry(String ipAddress1,String ipAddress2,String community) {
		FixNetworkGraph fixNetworkGraph = topoService.getFixNetworkGraph("topo").get(0);
		int total=0;
		String physAddress1=null;
		String physAddress2 = null;
		boolean flag1=false;
		boolean flag2=false;
		ArrayList<Host> hosts=fixNetworkGraph.getHosts();
		Iterator<Host> Ihosts=hosts.iterator();
		while(Ihosts.hasNext()) {
			Host host=Ihosts.next();
			if(!flag1) {
				if(host.getIpAddress().equals(ipAddress1)) {
					physAddress1=host.getMACAddress();
					flag1=true;
				}
			}
			if(!flag2) {
				if(host.getIpAddress().equals(ipAddress2)) {
					physAddress2=host.getMACAddress();
					flag2=true;
				}
			}
		}
		
		List<Interfaces> result=new ArrayList<>();
		DeviceResources dr1 = new DeviceResources();
		dr1.buildInterfaces(ipAddress1, community);
		List<Interfaces> list1=null;
		if(dr1.getInterfaces()==null) {
			list1=null;
			total+=0;
		}else {
			list1=ClassFomaterUtil.classFomater(dr1.getInterfaces());
			total+=list1.size();//暂时先这么写
		}
				
		
		DeviceResources dr2 = new DeviceResources();
		dr2.buildInterfaces(ipAddress2, community);
		List<Interfaces> list2=null;
		if(dr2.getInterfaces()==null) {
			list2=null;
			total+=0;
		}else {
			list2=ClassFomaterUtil.classFomater(dr2.getInterfaces());
			total+=list2.size();//暂时先这么写
		}
		
		
		System.out.println(ipAddress1+"的物理地址为"+physAddress1);
		System.out.println(ipAddress2+"的物理地址为"+physAddress2);
		for(Interfaces inter:list1) {
			if(inter.getIfPhysAddress().equals(physAddress1)) {
				result.add(inter);
				break;
			}
		}
		for(Interfaces inter:list2) {
			if(inter.getIfPhysAddress().equals(physAddress2)) {
				result.add(inter);
				break;
			}
		}
		result.stream().forEach(System.out::println);
		return R.ok().put("result", result);
	}
	@ResponseBody
	@RequestMapping("getTcpEntry")
	public R getTcpEntry(String ipAddress, String community) {
		logger.info("Hello----World");
		DeviceResources dr = new DeviceResources();
		dr.buildTcp(ipAddress, community);
		List<TcpProgram>list=ClassFomaterUtil.tcpFormater(dr.getTcp());
		int total=list.size();//暂时先这么写
		PageUtils pageUtil = new PageUtils(list, total, 10, 1);
		return R.ok().put("page", pageUtil);
	}
	@ResponseBody
	@RequestMapping("getAtEntry")
	public AtEntry getAtEntry(String ipAddress, String community) {
		logger.info("Hello----World");
		DeviceResources dr = new DeviceResources();
		dr.buildAt(ipAddress, community);
		return dr.getAt();
	}
	@ResponseBody
	@RequestMapping("getIcmpEntry")
	public IcmpEntry getIcmpEntry(String ipAddress, String community) {
		logger.info("Hello----World");
		DeviceResources dr = new DeviceResources();
		dr.buildIcmp(ipAddress, community);
		return dr.getIcmp();
	}
	@ResponseBody
	@RequestMapping("getDeviceActualResources")
	public DeviceActualResources getDeviceActualResources(String ipAddress, String community) {
		DeviceActualResources dar = ipService.getDeviceActualResources(ipAddress, community);
		return dar;
	}

	@ResponseBody
	@RequestMapping("getDeviceStaticResources")
	public DeviceStaticResources getDeviceStaticResources(String ipAddress, String community) {
		DeviceStaticResources dsr = ipService.getDeviceStaticResources(ipAddress, community);
		return dsr;
	}
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping("getEquipmentList") public R getEquipmentList() {
	 * 
	 * return R.ok(); }
	 */

	@RequestMapping("getSituation")
	@ResponseBody
	public R getSituation(String ip) {
		DeviceActualResources dar = ipService.getDeviceActualResources(ip, "public");
		return R.ok().put("situation", dar);
	}

	@RequestMapping("getFlowMoni")
	@ResponseBody
	public R getFlowMoni(Equipment Equipment, Integer page, Integer limit) {
		ArrayList<DeviceStaticResources> deviceStaticResourcesList = new ArrayList<DeviceStaticResources>();
		ArrayList<Ip> ipArrayList = ipService.queryAll();
		if (ipArrayList.size() < 1) {
			FoundationDiscovery d = new FoundationDiscovery();
			d.netSystemSearch("public");
			NetworkGraph networkGraph = d.getNetworkGraphList().get(0);
			networkGraph.getNodes().stream().forEach(node -> {
				if (!node.getIp().equals("127.0.0.1")) {
					Ip ip = new Ip(node.getIp());
					ipArrayList.add(ip);
					String oid = UUID.randomUUID().toString().replaceAll("-", "");

					ip.setoId(oid);
					ipService.save(ip);
				}
			});
		}
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("equipmentName", Equipment.getEquipmentName());
		map.put("ip", Equipment.getIp());
		List<Equipment> euipmentList = equipmentService.queryList(map);
		if (euipmentList.size() < 1) {
			ipArrayList.stream().forEach(ip -> {
				DeviceStaticResources dsr = ipService.getDeviceStaticResources(ip.getIp(), "public");
				if (dsr != null) {
					dsr.setId((int) (Math.random() * 1000));
					deviceStaticResourcesList.add(dsr);
					Equipment equipment = new Equipment();
					equipment.setEquipmentName(dsr.getSysName());
					equipment.setoId(ip.getoId());
					equipment.setIp(ip.getIp());
					equipment.setOnlineStatus(1);
					equipment.setOnlineTime("123");
					equipment.setWorkPattern("初始化");
					equipmentService.save(equipment);
					euipmentList.add(equipment);

				}
			});
			deviceStaticResourcesList.stream().forEach(System.out::println);
		}
		List<DeviceActualResources> darList = new ArrayList<>();

		for (Equipment e : euipmentList) {
			System.out.println("---------------------------------" + e.getIp());
			if (e.getIp().equals("172.17.0.1")) {
				continue;
			}
			DeviceActualResources dar = ipService.getDeviceActualResources(e.getIp(), "public");
			darList.add(dar);
		}
		int total = darList.size();
		PageUtils pageUtil = new PageUtils(darList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}
	@RequestMapping("getTotalByEquipment")
	@ResponseBody
	public R getTotalByEquipment() {
		Map<String,String> result =new HashMap<String,String>();
		result.put("total", String.valueOf(equipmentService.queryTotal()));
		return R.ok().put("result", result);
		
	}
	@RequestMapping("getTotalByIp")
	@ResponseBody
	public R getTotalByIp() {
		Map<String,String> result =new HashMap<String,String>();
		result.put("total", String.valueOf(ipService.queryTotal()));
		return R.ok().put("result", result);
		
	}
}
