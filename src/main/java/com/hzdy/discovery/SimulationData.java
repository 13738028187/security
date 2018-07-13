package com.hzdy.discovery;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import java.util.Set;

import com.hzdy.discovery.entity.Switch;

public class SimulationData {

	// 子网内设备IP与设备信息的map
	private Map<String, Host> subnetIpDevices = new LinkedHashMap<>();
	// 子网内设备mac与设备信息的map
	private Map<String, Host> subnetMacDevices = new LinkedHashMap<>();
	// 子网内交换机ip与交换机信息的map
	private Map<String, Host> subnetIpSwitchs = new LinkedHashMap<>();
	// 子网内交换机mac与交换机信息的map
	private Map<String, Host> subnetMacSwitchs = new LinkedHashMap<>();
	// �?要用来展示拓扑结构树的节点信�?
	private TreeNode[] nodes;
	// 全局变量TreeNode数组的索�?
	private int nodeIndex = 1;
	// 网关IP
	//private String gatewayIp = IPRouteAndAddrTableUtils.GetDefaultGateway("public");
	private float gatewayId=0;
	// Map<IP（交换机的IP地址�?,Set<Mac>（交换机除了接入网络的其他端口需要转发的mac地址�?> 用于全局存储�?有子网内交换机对应的mac地址
	private Map<String, Set<String>> subnetSwitchsNextMacs;
	// 已经生成节点信息的计算机（type = 3）节点的set 利用这个来防止重复生成计算机节点
	private Set<String> hasNodeComputerMac = new HashSet<>();

	public TreeNode[] analysis(List<Host> MyHosts,List<Switch> ls,String subnet,String gatewayIp){ 
	  //初始�?,主要分为交换机和主机
	  MyHosts.stream().forEach(h->{
		  subnetIpDevices.put(h.getIpAddress(), h);
	  });
	  MyHosts.stream().forEach(h->{
		  subnetMacDevices.put(h.getMACAddress(), h);
	  });
	  ls.stream().forEach(s->{
		  subnetIpSwitchs.put(s.getHost().getIpAddress(), s.getHost());
	  });
	  ls.stream().forEach(s->{
		  subnetMacSwitchs.put(s.getHost().getMACAddress(), s.getHost());
	  });
      //根据子网在线设备数初始化TreeNode数组  因为要加入默认网�?,�?以多加一�?
      nodes = new TreeNode[subnetIpDevices.size()+1];  
        
      subnetSwitchsNextMacs = new HashMap<>();  
      for(Entry<String, Host> entry : subnetIpSwitchs.entrySet()){  
    
          Set<String> set = getSwitchPortMac(entry.getKey(),ls);  
          subnetSwitchsNextMacs.put(entry.getKey(), set);  
            
      }  
      boolean rootNodesTure=false;
      for( Host h:MyHosts) {
    	  if(h.getIpAddress().equals(gatewayIp)){
    		  rootNodesTure=true;
    		  nodes[0] = new TreeNode(h.getIpAddress(), "0", h.getIpAddress(),h.getMACAddress(), true,subnet,1);  
    		  break;
    	  }
      }; 
      if(!rootNodesTure) {
    	  nodes[0] = new TreeNode(gatewayIp, "0", gatewayIp, true,subnet,1);  
      }
          
      //循环生成拓扑结构树，每生成一个分支，就把该分支删除掉，直到将网关下所有分支全部生成完�?   
      while(subnetSwitchsNextMacs.size() > 0){  
          generateNodeTree(gatewayIp,subnetSwitchsNextMacs,subnet);  
      }  
      //对于子网内交换机与交换机内主机之类的节点已经成功生成拓扑结构�?,下来对于不是在交换机内的主机,即直接连接路由器的设备生成拓扑树
      int j=nodeIndex;
      Iterator<Host> it=MyHosts.iterator();
      while(it.hasNext()) {
    	  Host host=it.next();
    	  for(int i=0;i<nodeIndex;i++) {
    		  if(!host.getIpAddress().equals(nodes[i].getIp())){   		
    			      
    				nodes[j] = new TreeNode(host.getIpAddress(), gatewayIp,host.getIpAddress(), host.getMACAddress(),true,host.getSubNet(),host.getType());
    				j++;
    		  }
    	  }
      }
      nodeIndex+=j;
      //输出结果
      for(TreeNode node : nodes){  
          try{  
              System.out.println("ip:"+node.getIp()+"  pIp:"+node.getpIp()+"  isOpen:"+node.isOpen());  
          }catch (Exception e){  
              break;  
          }  
      }  
      return nodes;
  }

	/**
	 * 这个方法采用了�?�归
	 * 我们是�?�过snmp获取的交换机下挂设备mac地址，获取的下挂设备的mac地址集合包括�?有的直连设备（包括交换机和其他设备）和直连设备下挂设备的mac地址的集�?
	 * 给上面的话举个例子交换机1下挂交换�?2�?3，和设备a�?2下挂设备b，则1下挂设备2�?3、a、b�?2下挂设备b�?3则没有下挂设备，如果设备b也是交换机，
	 * 下挂了c、d，则c和d既属�?1，也属于2 这里也可以看出父节点的下挂设备包含了子节点和子节点的下挂设备，所有父节点下挂设备�?定比子节点下挂设备多
	 * 
	 * @param parentIp
	 *            父节点的ip（父节点可以是网关或交换机）
	 * @param switchsNextMacs
	 *            父节点下的所有支持snmp的交换机及交换机下挂的设备的mac
	 *            地址Map<String（交换机IP�?,Set<String>（交换机下挂设备的mac地址集合�?>
	 * 
	 *            基于上面的限定，我们首先要得出一个结论，即拥有最多下挂设备的交换机一定是parentIp的直接子节点�?
	 *            这个可以反证�?个，如果不是直接子节点的话那么它和parentIp直接�?定还有其他交换机，即它的父节点，但是根据上面的限定，子节点的下挂设备不可能大于父节点的下挂设�?
	 * 
	 *            首先以默认网关为父节点，找到下挂设备�?多的交换机，这样这个交换机就可以确定是跟默认网关直连的了，然后再看这个交换机下的�?有下挂设备是否包含交换机，如果包含则
	 *            将这个交换机作为parentIp，将�?有下挂的交换机作为switchsNextMacs，再次调用自身，直到没有下挂交换机为�?
	 */
	private void generateNodeTree(String parentIp, Map<String, Set<String>> switchsNextMacs,String subnet) {
		// 找出�?有交换机中下挂设备最多的那个交换�?
		String maxSwitchIp = getNextMaxSwitchIp(switchsNextMacs);

		Set<String> parentSet = null;
		if (subnetSwitchsNextMacs.containsKey(parentIp)) {
			// 获取当前处理的交换机的父设备的所有下挂设�?
			parentSet = subnetSwitchsNextMacs.get(parentIp);
			//判断父集合是否是空的
			if (null != parentSet) {
				// 将当前处理的交换机从父设备的下挂设备中剔除，直到剔除干净，才算处理完了父设备
				// @@标记A
				parentSet.remove(subnetIpDevices.get(maxSwitchIp).getMACAddress());
			}
		}
		// 利用上面结论，这个下挂设备最多的交换机是parentIp直连的，生成节点数据
		nodes[nodeIndex] = new TreeNode(maxSwitchIp, parentIp, maxSwitchIp,subnetIpDevices.get(maxSwitchIp).getMACAddress(), true,subnetIpDevices.get(maxSwitchIp).getSubNet(),2);
		nodeIndex++;

		// 获取当前处理的有�?多下挂设备的交换机的�?有下挂设�?
		Set<String> nextMacs = subnetSwitchsNextMacs.get(maxSwitchIp);

		// <ip,Set<mac>> 用于存储当前交换机下的下挂交换机
		Map<String, Set<String>> nextMap = new HashMap<>();

		for (String mac : nextMacs) {
			//如果子网全设备列表下有此MAC地址,并且,此根据此地址获取的设备类型为交换机时
			if (subnetMacDevices.containsKey(mac) && subnetMacDevices.get(mac).getType()==2) {
				//获取此地�?相应的IP地址
				String switchIp = subnetMacDevices.get(mac).getIpAddress();
				//添加到用这个MAP
				nextMap.put(switchIp, switchsNextMacs.get(switchIp));
				// 把是当前�?多下挂设备的交换机的�?有下挂交换机从它的父交换机的下挂设备中清除，
				// 这样当回到处理父交换机时就不用再处理这些肯定不是跟她直连的交换机�?
				if (null != parentSet) {
					parentSet.remove(mac);
				}
			}
		}

		while (nextMap.size() > 0) {
			// System.out.println("maxSwitchIp:"+maxSwitchIp+" nextMap
			// size:"+nextMap.size());
			// 当下挂交换机的数量大�?0时�?�归调用自己
			generateNodeTree(maxSwitchIp, nextMap,subnet);

			// 调用完成后重新获取当前处理交换机的下挂交换机，如果还大于0继续
			// 交换机的剔除是在@@标记A处调用的
			nextMap.clear();

			for (String mac : nextMacs) {
				if (subnetMacDevices.containsKey(mac) && subnetMacDevices.get(mac).getType()==2) {
					String switchIp = subnetMacDevices.get(mac).getIpAddress();
					nextMap.put(switchIp, switchsNextMacs.get(switchIp));
				}
			}
		}

		// 进入到这代表当前处理的交换机已经无下挂的交换机，可以对其他的主机下挂设备进行节点数据的生�?
		// 要注意的是：因为父节点包含子节点和子节点的所有子节点，所有有些子节点的下挂设备已经生成过了，就需要存储在hasNodeComputerMac里面
		// 把子节点的下挂设备剔除掉就是当前交换机自己的下挂设备�?
		for (String mac : nextMacs) {
			if (subnetMacDevices.containsKey(mac) && !hasNodeComputerMac.contains(mac)) {
				String computerIp = subnetMacDevices.get(mac).getIpAddress();
				String subNetAddr=subnetMacDevices.get(mac).getSubNet();
				int type = subnetMacDevices.get(mac).getType();
				nodes[nodeIndex] = new TreeNode(computerIp,maxSwitchIp, computerIp,mac,true,subNetAddr,type);
				hasNodeComputerMac.add(mac);
				nodeIndex++;
			}
		}

		// �?后把自己从需要处理的交换机集合中剔除掉就行了
		subnetSwitchsNextMacs.remove(maxSwitchIp);
	}

	// 找出�?有交换机中下挂设备最多的�?个交换机
	private static String getNextMaxSwitchIp(Map<String, Set<String>> switchsNextMacs) {
		int maxCount = -1;
		String maxSwitchIp = null;
		for (Entry<String, Set<String>> entry : switchsNextMacs.entrySet()) {
			if (entry.getValue().size() > maxCount) {
				maxCount = entry.getValue().size();
				maxSwitchIp = entry.getKey();
			}
		}
		return maxSwitchIp;
	}

	private static Set<String> getSwitchPortMac(String targetIp,List<Switch> ls) {

		Set<String> result = new HashSet<>();
		ls.stream().forEach(s->{
			if(s.getHost().getIpAddress().equals(targetIp)) {
				s.getSwitchLearnedMac().stream().forEach(m->{
					result.add(m);
				});
			}
		});
		return result;

	}
	public TreeNode[] getNodes() {
		return nodes;
	}
}
