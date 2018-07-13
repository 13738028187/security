package com.hzdy.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hzdy.entity.Interfaces;
import com.hzdy.manager.entity.InterfacesEntry;
import com.hzdy.manager.entity.TcpEntry;
import com.hzdy.manager.entity.TcpProgram;
import com.hzdy.manager.entity.UdpEntry;
import com.hzdy.manager.entity.UdpProgram;

public class ClassFomaterUtil {
	public static List<Interfaces>  classFomater(InterfacesEntry interfacesEntry){
		List<Interfaces> list=new ArrayList<>();
		Map<String, List<String>> map=interfacesEntry.getIfTable().getMap();
		for(int i=0;i<map.get("1.3.6.1.2.1.2.2.1.2").size();i++){
			Interfaces interfaces=new Interfaces();		
			interfaces.setInterfaceName(map.get("1.3.6.1.2.1.2.2.1.2").get(i));
			interfaces.setIfType(map.get("1.3.6.1.2.1.2.2.1.3").get(i));
			interfaces.setIfMtu(map.get("1.3.6.1.2.1.2.2.1.4").get(i));			
			interfaces.setIfPhysAddress(map.get("1.3.6.1.2.1.2.2.1.6").get(i));		
			interfaces.setIfLastChange(map.get("1.3.6.1.2.1.2.2.1.9").get(i));			
			interfaces.setIfInOctets(map.get("1.3.6.1.2.1.2.2.1.10").get(i));
			interfaces.setIfInUcastPkts(map.get("1.3.6.1.2.1.2.2.1.11").get(i));
			interfaces.setIfInNUcastPkts(map.get("1.3.6.1.2.1.2.2.1.12").get(i));
			interfaces.setIfInDiscards(map.get("1.3.6.1.2.1.2.2.1.13").get(i));
			interfaces.setIfInErrors(map.get("1.3.6.1.2.1.2.2.1.14").get(i));
			interfaces.setIfInUnknownProtos(map.get("1.3.6.1.2.1.2.2.1.15").get(i));
			interfaces.setIfOutOctets(map.get("1.3.6.1.2.1.2.2.1.16").get(i));
			interfaces.setIfOutUcastPkts(map.get("1.3.6.1.2.1.2.2.1.17").get(i));
			interfaces.setIfOutNUcastPkts(map.get("1.3.6.1.2.1.2.2.1.18").get(i));
			interfaces.setIfOutDiscards(map.get("1.3.6.1.2.1.2.2.1.19").get(i));
			interfaces.setIfOutErrors(map.get("1.3.6.1.2.1.2.2.1.20").get(i));
			interfaces.setIfOutQLen(map.get("1.3.6.1.2.1.2.2.1.21").get(i));
			interfaces.setIfSpecific(map.get("1.3.6.1.2.1.2.2.1.22").get(i));
			list.add(interfaces);
		}
		return list;
	}
	public static List<UdpProgram> updFormater(UdpEntry udp){
		List<UdpProgram> list=new ArrayList<>();
		Map<String, List<String>> map=udp.getUdpTable().getMap();
		for(int i=0;i<map.get("1.3.6.1.2.1.7.5.1.2").size();i++){
			UdpProgram udpProgram=new UdpProgram();
			udpProgram.setPort(map.get("1.3.6.1.2.1.7.5.1.2").get(i));
			udpProgram.setIp(map.get("1.3.6.1.2.1.7.5.1.1").get(i));
			list.add(udpProgram);
		}
		return list;
	}
	public static List<TcpProgram> tcpFormater(TcpEntry tcp){
		List<TcpProgram> list=new ArrayList<>();
		Map<String, List<String>> map=tcp.getTcpTable().getMap();
		for(int i=0;i<map.get("1.3.6.1.2.1.6.13.1.3").size();i++){
			TcpProgram tcpProgram=new TcpProgram();
			tcpProgram.setTcpConnState(map.get("1.3.6.1.2.1.6.13.1.1").get(i));
			tcpProgram.setTcpConnLocalAddress(map.get("1.3.6.1.2.1.6.13.1.2").get(i));
			tcpProgram.setTcpConnLocalPort(map.get("1.3.6.1.2.1.6.13.1.3").get(i));
			tcpProgram.setTcpConnRemAddress(map.get("1.3.6.1.2.1.6.13.1.4").get(i));
			tcpProgram.setTcpConnRemPort(map.get("1.3.6.1.2.1.6.13.1.5").get(i));
			list.add(tcpProgram);
		}
		return list;
	}
}
