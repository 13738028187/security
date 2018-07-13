package com.hzdy.discovery;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.VariableBinding;

import com.hzdy.discovery.entity.IpAddrEntry;
import com.hzdy.discovery.entity.IpAddressTable;
import com.hzdy.discovery.entity.IpNetToMediaEntry;
import com.hzdy.discovery.entity.IpNetToMediaTable;
import com.hzdy.discovery.entity.IpRouteEntry;
import com.hzdy.discovery.entity.IpRouteTable;
import com.hzdy.discovery.entity.QuickFind;
import com.hzdy.discovery.entity.Router;
import com.hzdy.discovery.entity.Switch;
import com.hzdy.discovery.entity.json.Link;
import com.hzdy.discovery.entity.json.NetworkGraph;
import com.hzdy.discovery.entity.json.NetworkRoutes;
import com.hzdy.discovery.entity.json.Node;
import com.hzdy.discovery.oid.code.IpAddrOid;
import com.hzdy.discovery.oid.code.NetToMediaOid;
import com.hzdy.discovery.oid.code.RouterOid;
import com.hzdy.discovery.oid.code.SNMPCODE;
import com.hzdy.utils.SnmpUtils;
import com.hzdy.utils.SnmpV2Utils;

public class FoundationDiscovery {
	private ArrayList<Device> devices = new ArrayList<>();
	private ArrayList<NetworkRoutes> NetworkRoutesList = new ArrayList<>();
	private ArrayList<NetworkGraph> NetworkGraphList = new ArrayList<>();
	private ArrayList<String> discoveriedRouteQueueResult=new ArrayList<>();
	private ArrayList<Host> hosts = new ArrayList<>();
	// 路由器队列
	public ArrayList<Router> routerQueue = new ArrayList<>();
	// 默认路由器ip地址列表
	public ArrayList<String> defaultRouteIpList = new ArrayList<>();

	public ArrayList<Edge> links = new ArrayList<>();

	public ArrayList<NetworkRoutes> getNetworkRoutesList() {
		return NetworkRoutesList;
	}

	public ArrayList<NetworkGraph> getNetworkGraphList() {
		return NetworkGraphList;
	}

	public void showtNetworkRoutesList() {
		NetworkRoutesList.stream().forEach(System.out::println);
	}

	public void showNetworkGraphList() {
		NetworkGraphList.stream().forEach(System.out::println);
	}

	public static void main(String args[]) {
/*		FoundationDiscovery d = new FoundationDiscovery();
		d.netSystemSearch("public");
		System.out.println("searchByCacheArp");
		
		 * ArrayList<String> list=searchByCacheArp("10.0.0.1","public");
		 * list.stream().forEach(System.out::println);
		 
		System.out.println("-------------show-----------------");
		d.showNetworkGraphList();
		d.showtNetworkRoutesList();
		*/
		FoundationDiscovery d = new FoundationDiscovery();
		d.netSystemSearch("public");
		NetworkGraph networkGraph = d.getNetworkGraphList().get(0);
		networkGraph.getNodes().stream().forEach(node -> {
			if (!node.getIp().equals("127.0.0.1")) {
				Ip ip = new Ip(node.getIp());
				System.out.println(ip.getIp());
			}
		});
	}

	public void netSystemSearch(String community) {
		linkLayerSearch(community, "192.168.1.1");
	}
    public InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException(
                    "Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }
	public void linkLayerSearch(String community, String defaultRouteIp) {
		InetAddress serverIp = null;
		boolean isServer = false;
		try {
			serverIp = InetAddress.getLocalHost();
			System.out.println("执行系统的本地地址:" +getLocalHostLANAddress().getHostAddress());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		// 存储网络层算法执行结果，并获取子网列表
		HashMap<String, String> subNet = netWorkLayerSearch(community, defaultRouteIp);
		// 初始化活动设备列表
		hosts = searchHostsByIpNetMediaTable(defaultRouteIp, community);
		/*
		 * defaultRouteIpList.stream().forEach(r->{ try { hosts.add(new
		 * Host(r,getMac(r),1)); } catch (SocketException | UnknownHostException e) {
		 * e.printStackTrace(); } });
		 */
		System.out.println("目前活动的主机设备有:有错误");
		hosts.stream().forEach(System.out::println);
		hosts.stream().forEach(h -> {
			// public Device(String name, int type, String netLocation, String protocol,
			// String status) {
			if (h.getType() == -1) {
				devices.add(new Device(h.getIpAddress(), h.getType(), community, "snmp协议", "1"));
			} else {
				devices.add(new Device(h.getIpAddress(), h.getType(), community, "不支持或未开启snmp协议", "1"));
			}
		});
		QuickFind qf = new QuickFind();
		System.out.println("------------开始进行链路层的拓扑发现-----------");
		for (int i = 0; i < routerQueue.size(); i++) {
			if (routerQueue.get(i) != null) {
				System.out.println("当前的路由器的索引:" + i);
				routerQueue.get(0).getIpAddressTable().getIpAddrEntry().stream().forEach(inter -> {
					System.out.println("接口ip为:" + inter.getIpAdEntAddr());
					List<String> activeHosts = new ArrayList<>();
					String currentIp = inter.getIpAdEntAddr();
					qf.addValue(currentIp);
					// 初始化此子网段下的主机列表
					List<Host> subNetHosts = new ArrayList<>();
					// 对每个活动的设备进行遍历
					for (int j = 0; j < hosts.size(); j++) {
						Host h = hosts.get(j);
						System.out.println("当前ip地址为" + h.getIpAddress());
						// 当前的活动设备的ip地址与此子网的掩码比较
						String hostAndMackResult = andCompute(h.getIpAddress(), inter.getIpAdEntNetMask());
						String routerAndMackResult = andCompute(currentIp, inter.getIpAdEntNetMask());
						System.out.println("hostAndMackResult结果:" + hostAndMackResult);
						System.out.println("routerAndMackResult结果:" + routerAndMackResult);
						// 结果是和子网的网段比较相同时
						if (hostAndMackResult.equals(routerAndMackResult)) {
							// 将此活动设备的子网
							qf.addValue(h.getIpAddress());
							qf.union(currentIp, h.getIpAddress());
							h.setSubNet(currentIp);
							subNetHosts.add(h);
							links.add(new Edge(h.getIpAddress(), currentIp));
							// hosts.remove(j);
						}
					}
					// 对每个活动的设备进行遍历
					subNetHosts.stream().forEach(h -> {
						h.setType(deviceType(h.getIpAddress(), community));
					});
					System.out.println("分析完毕....");
					System.out.println("对每个设备进行设备类型的判断:");
					System.out.println("---[-1:未知设备];\n---[1:路由器];\n---[2:交换机];\n---[3:服务器];\n---[4:主机]");
					System.out.println("---找到运行系统的服务器---");
					/*
					 * // 设置服务器的主机 if (!isServer) { isServer =
					 * isServer(serverIp.getHostAddress(),currentIp, inter.getIpAdEntNetMask()); if
					 * (isServer) { final String fserverIP = serverIp.getHostAddress(); // 是服务器,修改类型
					 * subNetHosts.stream().forEach(a -> { if (a.getIpAddress().equals(fserverIP)) {
					 * a.setType(3); System.out.println("---找到�?:" + fserverIP + "---"); } }); } }
					 */
					// 从队列中找出�?有的类型�?2的设�?
					List<Host> switchList = getSwitchList(subNetHosts);
					// 对每个交换机进行类型转换
					List<Switch> ls = new ArrayList<Switch>();
					// 查询对每个交换机的地�?转发�?
					switchList.stream().forEach(s -> {
						// 对交换机进行分析
						ls.add(SnmpUtils.getInstance().snmpBulkFdbTable(s));
					});
					// 查看交换机的信息
					System.out.print("查看交换机信�?:[");
					ls.stream().forEach(s -> {
						System.out.println(s.getHost().getIpAddress());
					});
					System.out.println("]");
					// 查看主机信息
					System.out.println("查看主机信息:[");
					subNetHosts.stream().forEach(System.out::println);
					System.out.println("]");
				});
			}
		}
		qf.getId().stream().forEach(System.out::println);
		qf.getPd().stream().forEach(System.out::println);
		NetworkGraph ng = new NetworkGraph();
		qf.getId().stream().forEach(ip -> {
			if(ip.contains("127.0.0.1")||ip.contains("172.17.0.1")) {
				
			}else {
				boolean flag=true;
				for(Node node:ng.getNodes()){
					if(node.getIp().equals(ip)) {
						flag=false;
					}
				};
				if(flag) {
					Node node=new Node(ip, ip);
					node.setType("2");
					for(String e:discoveriedRouteQueueResult) {
						if(ip.equals(e)) {
							node.setType("1");
						}
					};
					ng.addNode(node);
				}
			}
		});
		
		links.stream().forEach(System.out::println);
		links.stream().forEach(link -> {
			ng.addLink(new Link(link.getSource(), link.getTarget()));
		});
		ng.setHosts(hosts);
		NetworkGraphList.add(ng);
		for(int i=0;i<discoveriedRouteQueueResult.size()-1;i++) {
			for(int j=1;i<discoveriedRouteQueueResult.size();i++) {
				ng.addLink(new Link(discoveriedRouteQueueResult.get(i),discoveriedRouteQueueResult.get(j)));
			}
		}
	}

	public HashMap<String, String> netWorkLayerSearch(String community, String defaultRouteIp) {
		System.out.println("-------网络层发现-----------");
		// 未检查路由队列
		LinkedList<String> discoveringRouteQueue = new LinkedList<>();
		// 已发现路由队列
		ArrayList<String> discoveriedRouteQueue = new ArrayList<>();
		// 子网队列
		HashMap<String, String> subNetQueue = new HashMap<>();
		ArrayList<String> topoInfoQueue = new ArrayList<>();
		// 添加到未检查路由队列
		discoveringRouteQueue.add(defaultRouteIp);
		// 开始广度遍历
		while (!discoveringRouteQueue.isEmpty()) {
			String currentRouteIp = discoveringRouteQueue.getLast();
			System.out.println(currentRouteIp);
			System.out.println(community);
			Router currentRouter = buildRouter(currentRouteIp, community);
			System.out.println(currentRouter);
			if (currentRouteIp.equals(defaultRouteIp)) {
				currentRouter.getIpAddressTable().getIpAddrEntry().stream().forEach(r -> {
					defaultRouteIpList.add(r.getIpAdEntAddr());
				});
			}
			routerQueue.add(currentRouter);
			if (currentRouter != null) {
				ArrayList<IpAddrEntry> currentIpAddrTable = currentRouter.getIpAddressTable().getIpAddrEntry();
				for (int i = 0; i < currentIpAddrTable.size(); i++) {
					// 得到一条地址表条目
					IpAddrEntry currentIpAddrEntry = currentIpAddrTable.get(i);
					subNetQueue.put(
							andCompute(currentIpAddrEntry.getIpAdEntAddr(), currentIpAddrEntry.getIpAdEntNetMask()),
							currentIpAddrEntry.getIpAdEntNetMask());
				}

				ArrayList<IpRouteEntry> currentIpRouteTable = currentRouter.getIpRouteTable().getIpRouteEntry();
				for (int i = 0; i < currentIpRouteTable.size(); i++) {
					// 得到一条路由表记录
					IpRouteEntry currentIpRouteEntry = currentIpRouteTable.get(i);
					// 判断条件
					if (currentIpRouteEntry.getIpRouteType() == 4
							&& !isContainsIp(discoveriedRouteQueue, currentRouteIp)) {
						discoveringRouteQueue.add(currentIpRouteEntry.getIpRouteNextHop());
						if (!isContainsIp(topoInfoQueue, currentIpRouteEntry.getIpRouteNextHop())) {
							topoInfoQueue.add(currentIpRouteEntry.getIpRouteNextHop());
						}
					} else if (currentIpRouteEntry.getIpRouteType() == 3) {
						currentRouter.getIpAddressTable().getIpAddrEntry().stream().forEach(h -> {
							if (!isContainsIp(topoInfoQueue, h.getIpAdEntAddr())) {
								topoInfoQueue.add(h.getIpAdEntAddr());
							}
						});
					}
				}
			}

			discoveriedRouteQueue.add(currentRouteIp);
			discoveringRouteQueue.remove();
		}
		System.out.println("已发现的路由个数：" + discoveriedRouteQueue.size() + ",每个路由器的一个端口ip地址(一个路由器有多个ip地址):");

		discoveriedRouteQueue.stream().forEach(System.out::println);
		discoveriedRouteQueueResult.addAll(discoveriedRouteQueue);
		ArrayList<String> result = new ArrayList<>();
		for (Entry<String, String> entry : subNetQueue.entrySet()) {
			if (!entry.getKey().equals("127.0.0.0")) {
				result.add(entry.getKey());
			}
		}
		System.out.println("查看路由实体类信息");
		routerQueue.stream().forEach(System.out::println);
		NetworkRoutes nr = new NetworkRoutes("NetworkRoutes", "rip2", "1.0", "metric");
		System.out.println(routerQueue.size());
		for (int i = 0; i < routerQueue.size(); i++) {
			if (routerQueue.get(i) != null) {
				nr.setRouteId(routerQueue.get(i).getIpAddressTable().getIpAddrEntry().get(0).getIpAdEntAddr());
				routerQueue.get(i).getIpRouteTable().getIpRouteEntry().stream().forEach(ipr -> {
					if (ipr != null) {
						nr.addRoute(ipr.getIpRouteDest(), ipr.getIpRouteNextHop(), "null", 0);
					}
				});
			}
		}
		NetworkRoutesList.add(nr);
		System.out.println("-----------");
		System.out.println(nr.toString());
		System.out.println("查看子网信息");
		for (Entry<String, String> entry : subNetQueue.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		// 查看所有网关
		System.out.println("查看topoInfo信息(总共有多少个网关)");
		topoInfoQueue.stream().forEach(System.out::println);
		// 获取路由器的ip地址
		System.out.println("-------网络层结束-----------");
		return subNetQueue;
	}

	private Router buildRouter(String currentRouteIp, String community) {
		Router router = new Router();
		List<VariableBinding> result = Walk(currentRouteIp, community, RouterOid.IpRouteIfIndex);
		if (result.isEmpty()) {
			return null;
		} else {
			router.setIpAddressTable(buildIpAddressTable(currentRouteIp, community));
			router.setIpRouteTable(buildIpRouteTable(currentRouteIp, community));
			return router;
		}
	}

	private void forResultByRouteEntry(ArrayList<IpRouteEntry> ipRouteEntryList, List<VariableBinding> result,
			String method, String type) {
		Class<?> ire = IpRouteEntry.class;
		Class<?> methodType = null;
		switch (type) {
		case "int":
			methodType = int.class;
			break;
		case "String":
			methodType = String.class;
			break;

		}
		Method m;
		try {
			m = ire.getDeclaredMethod(method, methodType);
			if (methodType == int.class) {
				for (int i = 0; i < result.size(); i++) {
					m.invoke(ipRouteEntryList.get(i), result.get(i).getVariable().toInt());
				}
			} else {
				for (int i = 0; i < result.size(); i++) {
					m.invoke(ipRouteEntryList.get(i), result.get(i).getVariable().toString());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private IpRouteTable buildIpRouteTable(String ip, String community) {
		IpRouteTable ipRouteTable = new IpRouteTable();
		ipRouteTable.setIpRouteEntry(buildIpRouteEntryList(ip, community));
		return ipRouteTable;

	}

	private ArrayList<IpRouteEntry> buildIpRouteEntryList(String ip, String community) {
		List<VariableBinding> result = Walk(ip, community, RouterOid.IpRouteDesk);
		ArrayList<IpRouteEntry> ipRouteEntryList = new ArrayList<>(result.size());
		for (int i = 0; i < result.size(); i++) {
			IpRouteEntry ipRouteEntry = new IpRouteEntry();
			ipRouteEntryList.add(ipRouteEntry);
		}
		forResultByRouteEntry(ipRouteEntryList, result, "setIpRouteDest", "String");
		forResultByRouteEntry(ipRouteEntryList, Walk(ip, community, RouterOid.IpRouteAge), "setIpRouteAge", "String");
		forResultByRouteEntry(ipRouteEntryList, Walk(ip, community, RouterOid.IpRouteIfIndex), "setIpRouteIfIndex",
				"int");
		forResultByRouteEntry(ipRouteEntryList, Walk(ip, community, RouterOid.IpRouteInfo), "setIpRouteInfo", "String");
		forResultByRouteEntry(ipRouteEntryList, Walk(ip, community, RouterOid.IpRouteMask), "setIpRouteMask", "String");
		forResultByRouteEntry(ipRouteEntryList, Walk(ip, community, RouterOid.IpRouteNextHop), "setIpRouteNextHop",
				"String");
		forResultByRouteEntry(ipRouteEntryList, Walk(ip, community, RouterOid.IprouteProto), "setIpRouteProto",
				"String");
		forResultByRouteEntry(ipRouteEntryList, Walk(ip, community, RouterOid.IpRouteType), "setIpRouteType", "int");

		System.out.println("放回之前的的数据:");
		ipRouteEntryList.stream().forEach(System.out::println);
		return ipRouteEntryList;
	}

	private IpAddressTable buildIpAddressTable(String ip, String community) {
		IpAddressTable ipAddressTable = new IpAddressTable();
		ipAddressTable.setIpAddrEntry(buildIpAddressEntryList(ip, community));
		return ipAddressTable;
	}

	private void forResultByIpAddrEntry(ArrayList<IpAddrEntry> ipAddressList, List<VariableBinding> result,
			String method, String type) {
		Class<?> iae = IpAddrEntry.class;
		Class<?> methodType = null;
		switch (type) {
		case "int":
			methodType = int.class;
			break;
		case "String":
			methodType = String.class;
			break;

		}
		Method m;
		try {
			m = iae.getDeclaredMethod(method, methodType);
			if (methodType.equals("int")) {
				for (int i = 0; i < result.size(); i++) {
					m.invoke(ipAddressList.get(i), result.get(i).getVariable().toInt());
				}
			} else {
				for (int i = 0; i < result.size(); i++) {
					m.invoke(ipAddressList.get(i), result.get(i).getVariable().toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ArrayList<IpAddrEntry> buildIpAddressEntryList(String ip, String community) {
		List<VariableBinding> result = Walk(ip, community, IpAddrOid.IpAdEntAddr);
		System.out.println(result.size());
		ArrayList<IpAddrEntry> ipAddressList = new ArrayList<>(result.size());
		for (int i = 0; i < result.size(); i++) {
			IpAddrEntry ipAddrEntry = new IpAddrEntry();
			ipAddressList.add(ipAddrEntry);
		}
		forResultByIpAddrEntry(ipAddressList, result, "setIpAdEntAddr", "String");
		forResultByIpAddrEntry(ipAddressList, Walk(ip, community, IpAddrOid.IpAdEntIfIndex), "setIpAdEntIfIndex",
				"String");
		forResultByIpAddrEntry(ipAddressList, Walk(ip, community, IpAddrOid.IpAdEntNetMask), "setIpAdEntNetMask",
				"String");
		forResultByIpAddrEntry(ipAddressList, Walk(ip, community, IpAddrOid.IpAdEntBcastAddr), "setIpAdentBcastAddr",
				"String");
		forResultByIpAddrEntry(ipAddressList, Walk(ip, community, IpAddrOid.IprouteReasmMaxSize),
				"setIpAdEntReasmMaxSize", "String");
		System.out.println("放回之前的的数据:");
		ipAddressList.stream().forEach(System.out::println);
		return ipAddressList;
	}

	public IpRouteTable buildIpRouteTable() {
		return new IpRouteTable();
	}

	public IpAddress setIpAddress(String ip) {
		return new IpAddress(ip);
	}

	private boolean subnetDiscovery() {
		return false;
	}

	private boolean interfaceDiscovery() {
		return false;
	}

	private boolean saveEntity() {
		return false;
	}

	public static boolean isContainsIp(List<String> lists, String ip) {
		for (int i = 0; i < lists.size(); i++) {
			if (lists.get(i).equals(ip)) {
				return true;
			}
		}
		return false;
	}

	public List<VariableBinding> Walk(String ip, String community, String oid) {
		return SnmpV2Utils.getInstance().Walk(ip, community, oid);
	}

	public String andCompute(String ip, String mask) {
		String[] sip = ip.split("\\.");
		String[] smask = mask.split("\\.");
		int[] rip = new int[4];
		for (int i = 0; i < 4; i++) {
			int bip = Integer.valueOf(sip[i]);
			int bmask = Integer.valueOf(smask[i]);
			rip[i] = (int) (bip & bmask);
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			sb.append(String.valueOf(rip[i]));
			sb.append(".");
		}
		return sb.toString().substring(0, sb.length() - 1);
	}

	private ArrayList<Host> searchHostsByIpNetMediaTable(String defaultGateway, String community) {
		List<String> ips = new ArrayList<String>();
		ArrayList<Host> hosts = new ArrayList<Host>();
		System.out.println("----------searchHostsByIpNetMediaTable--------------");
		List<VariableBinding> result = Walk(defaultGateway, community, SNMPCODE.IPNETTOMEDIANETSADDRESS);
		result.stream().forEach(a -> {
			ips.add(a.getVariable().toString());
		});
		result = Walk(defaultGateway, community, SNMPCODE.IPNETTOMEDIAPHYSADDRESS);
		for (int i = 0; i < result.size(); i++) {
			hosts.add(new Host(ips.get(i), result.get(i).getVariable().toString(), -1));
		}

		return hosts;
	}

	public boolean icmp(String host) {
		int timeOut = 3000;
		try {
			boolean status = InetAddress.getByName(host).isReachable(timeOut);
			System.out.println("发送数据包:" + status);
			return status;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	private int deviceType(String ipAddress, String community) {
		Map<Integer, String> result = SnmpUtils.getInstance().Walk(ipAddress, community, SNMPCODE.SYSSERVICE);

		int sysServices = 0;
		for (Entry<Integer, String> entry : result.entrySet()) {
			if (entry.getValue().equals("none")) {
				return -1;
			}
			sysServices = Integer.valueOf(entry.getValue());
		}
		result = SnmpUtils.getInstance().Walk(ipAddress, community, SNMPCODE.IPFORWARDING);
		int ipForwarding = 0;
		for (Entry<Integer, String> entry : result.entrySet()) {
			ipForwarding = Integer.valueOf(entry.getValue());
		}
		if (ipForwarding == 1 && sysServices == 4) {
			return 1;

		} else if (ipForwarding == 2 && sysServices == 2) {
			return 2;
		} else if (ipForwarding == 1 && sysServices >= 1 && sysServices <= 11) {
			return 1;
		} else if (ipForwarding == 2 && sysServices > 11) {
			return 4;
		} else {
			return 4;
		}
	}

	public static ArrayList searchByCacheArp(String defaultGateway, String community) {
		Map<Integer, String> result = SnmpUtils.getInstance().WalkOld(defaultGateway, community,
				SNMPCODE.IPNETTOMEDIATABLE);
		ArrayList<String> arps = new ArrayList<>();
		arps.add(defaultGateway);
		for (Entry<Integer, String> entry : result.entrySet()) {
			String value = entry.getValue();
			String[] ip = value.split("\\.");
			StringBuilder sb = new StringBuilder();
			sb.append(ip[ip.length - 4] + "." + ip[ip.length - 3] + "." + ip[ip.length - 2] + "." + ip[ip.length - 1]);
			arps.add(sb.toString());
		}
		result = SnmpUtils.getInstance().Walk(defaultGateway, community, SNMPCODE.SYSSERVICE);
		return arps;
	}

	private ArrayList<IpNetToMediaEntry> buildIpNetToMediaEntryList(String ip, String community) {
		List<VariableBinding> result = Walk(ip, community, NetToMediaOid.IpNetToMediaIfIndex);
		System.out.println(result.size());
		ArrayList<IpNetToMediaEntry> ipNetToMediaEntryList = new ArrayList<>(result.size());
		for (int i = 0; i < result.size(); i++) {
			IpNetToMediaEntry ipNetToMediaEntry = new IpNetToMediaEntry();
			ipNetToMediaEntryList.add(ipNetToMediaEntry);
		}
		forResultByIpNetToMediaEntry(ipNetToMediaEntryList, result, "setIpNetToMediaIfIndex", "String");
		forResultByIpNetToMediaEntry(ipNetToMediaEntryList, Walk(ip, community, NetToMediaOid.IpNetToMediaPhysAddress),
				"setIpNetToMediaPhysAddress", "String");
		forResultByIpNetToMediaEntry(ipNetToMediaEntryList, Walk(ip, community, NetToMediaOid.IpNetToMediaNetAddress),
				"setIpNetToMediaNetAddress", "String");
		forResultByIpNetToMediaEntry(ipNetToMediaEntryList, Walk(ip, community, NetToMediaOid.IpNetToMediaType),
				"setIpNetToMediaType", "String");
		System.out.println("IpToMeida的的数据:");
		ipNetToMediaEntryList.stream().forEach(System.out::println);
		return ipNetToMediaEntryList;
	}

	private void forResultByIpNetToMediaEntry(ArrayList<IpNetToMediaEntry> ipNetToMediaEntryList,
			List<VariableBinding> result, String method, String type) {
		Class<?> iae = IpNetToMediaEntry.class;
		Class<?> methodType = null;
		switch (type) {
		case "int":
			methodType = int.class;
			break;
		case "String":
			methodType = String.class;
			break;
		}
		Method m;
		try {
			m = iae.getDeclaredMethod(method, methodType);
			if (methodType.equals("int")) {
				for (int i = 0; i < result.size(); i++) {
					m.invoke(ipNetToMediaEntryList.get(i), result.get(i).getVariable().toInt());
				}
			} else {
				for (int i = 0; i < result.size(); i++) {
					m.invoke(ipNetToMediaEntryList.get(i), result.get(i).getVariable().toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private IpNetToMediaTable buildIpNetToMediaTable(String ip, String community) {
		IpNetToMediaTable ipNetToMediaTable = new IpNetToMediaTable();
		ipNetToMediaTable.setIpNetToMediaEntry(buildIpNetToMediaEntryList(ip, community));
		return ipNetToMediaTable;
	}

	private List<Host> getSwitchList(List<Host> myHosts) {
		List<Host> switchList = new ArrayList<Host>();
		myHosts.stream().forEach(h -> {
			if (h.getType() == 2) {
				switchList.add(h);
			}
		});
		return switchList;
	}

	private boolean isServer(String serverIp, String value, String key) {
		String hostAndMackResult = andCompute(serverIp, value);
		// 结果是和子网的网段比较相同时
		if (hostAndMackResult.equals(key)) {
			return true;
		} else {
			return false;
		}
	}

	public String getIpByMib(String value) {
		String[] ip = value.split("\\.");
		StringBuilder sb = new StringBuilder();
		sb.append(ip[ip.length - 4] + "." + ip[ip.length - 3] + "." + ip[ip.length - 2] + "." + ip[ip.length - 1]);
		return sb.toString();
	}

	public String getMac(String ip) throws SocketException, UnknownHostException {
		/*
		 * InetAddress ia=InetAddress.getByName(ip); System.out.println(ia);
		 * NetworkInterface.getByInetAddress(ia);
		 * if(NetworkInterface.getByInetAddress(ia).getHardwareAddress()!=null) { byte[]
		 * mac=NetworkInterface.getByInetAddress(ia).getHardwareAddress(); StringBuilder
		 * sb=new StringBuilder(); for(int i=0;i<mac.length;i++) { if(i!=0) {
		 * sb.append("-"); } int temp=mac[i]&0xff; String str=Integer.toHexString(temp);
		 * if(str.length()==1) { sb.append("0"+str); }else { sb.append(str); } } return
		 * sb.toString(); }else { return "未知"; }
		 */
		return "未知";
	}
}
