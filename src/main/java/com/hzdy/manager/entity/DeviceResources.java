package com.hzdy.manager.entity;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzdy.manager.utils.DeviceResourcesOid;
import com.hzdy.manager.utils.SnmpOperaUtil;

/**
 * 
 * what
 * 
 * @author kirohuji
 * @version 0.1
 */
public class DeviceResources {
	private String ipAddress;
	private InterfacesEntry interfaces;
	private AtEntry at;
	private IcmpEntry icmp;
	private IpEntry ip;
	private SnmpEntry snmp;
	private SystemEntry system;
	private TcpEntry tcp;
	private UdpEntry udp;

	public void buildInterfaces(String ipAddress, String community) {
		HashMap<String, String> test = new HashMap<String, String>();
		SnmpOperaUtil.snmpGet(test, ipAddress, community, DeviceResourcesOid.sysDesc);
		if (test.get("null") != null && test.get("null").equals("null")) {
		} else {
			test.clear();
			interfaces = new InterfacesEntry();
			Map<String, String> ifNumber = new HashMap<String, String>();
			SnmpOperaUtil.snmpGet(ifNumber, ipAddress, community, interfaces.getOid() + ".1.0");
			Map<String, List<String>> ifEntry = new HashMap<>();
			InterfacesEntry.IfTable ifTable = interfaces.new IfTable();
			Map<String, String> data = new HashMap<>();
			for (int i = 1; i <= ifTable.getNumber(); i++) {
				SnmpOperaUtil.snmpWalk(data, ipAddress, community, ifTable.getOid() + "." + i);
				ifEntry.put(ifTable.getOid() + "." + i, new ArrayList<String>());
				for (Entry<String, String> result : data.entrySet()) {
					ifEntry.get(ifTable.getOid() + "." + i).add(result.getValue());
				}
			}
			ifTable.setMap(ifEntry);
			interfaces.setMap(ifNumber);
			interfaces.setIfTable(ifTable);
		}
	}

	public void buildAt(String ipAddress, String community) {
		HashMap<String, String> test = new HashMap<String, String>();
		SnmpOperaUtil.snmpGet(test, ipAddress, community, DeviceResourcesOid.sysDesc);
		if (test.get("null") != null && test.get("null").equals("null")) {
		} else {
		at = new AtEntry();
		AtEntry.AtTable atTable = at.new AtTable();
		Map<String, List<String>> atEntry = new HashMap<>();
		Map<String, String> data = new HashMap<>();
		for (int i = 1; i <= atTable.getNumber(); i++) {
			SnmpOperaUtil.snmpWalk(data, ipAddress, community, atTable.getOid() + "." + i);
			atEntry.put(atTable.getOid() + "." + i, new ArrayList<String>());
			for (Entry<String, String> result : data.entrySet()) {
				atEntry.get(atTable.getOid() + "." + i).add(result.getValue());
			}
		}
		atTable.setMap(atEntry);
		at.setAtTable(atTable);
		}
	}

	public void buildIcmp(String ipAddress, String community) {
		HashMap<String, String> test = new HashMap<String, String>();
		SnmpOperaUtil.snmpGet(test, ipAddress, community, DeviceResourcesOid.sysDesc);
		if (test.get("null") != null && test.get("null").equals("null")) {
		} else {
		icmp = new IcmpEntry();
		Map<String, String> map = new HashMap<>();
		List<String> oids = new ArrayList<String>();
		for (int i = 1; i < icmp.getNumber(); i++) {
			oids.add(icmp.getOid() + "." + i + ".0");
		}
		for (int i = 1; i <= icmp.getNumber(); i++) {
			SnmpOperaUtil.snmpGetList(map, ipAddress, community, oids);
		}
		for (Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		icmp.setMap(map);
		}
	}

	public void buildSnmp(String ipAddress, String community) {
		HashMap<String, String> test = new HashMap<String, String>();
		SnmpOperaUtil.snmpGet(test, ipAddress, community, DeviceResourcesOid.sysDesc);
		if (test.get("null") != null && test.get("null").equals("null")) {
		} else {
		snmp = new SnmpEntry();
		Map<String, String> map = new HashMap<>();
		List<String> oids = new ArrayList<String>();
		for (int i = 1; i < snmp.getNumber(); i++) {
			oids.add(snmp.getOid() + "." + i + ".0");
		}
		for (int i = 1; i <= snmp.getNumber(); i++) {
			SnmpOperaUtil.snmpGetList(map, ipAddress, community, oids);
		}
		for (Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		snmp.setMap(map);
		}
	}

	public void buildSystem(String ipAddress, String community) {
		HashMap<String, String> test = new HashMap<String, String>();
		SnmpOperaUtil.snmpGet(test, ipAddress, community, DeviceResourcesOid.sysDesc);
		if (test.get("null") != null && test.get("null").equals("null")) {
		} else {
		system = new SystemEntry();
		Map<String, String> map = new HashMap<>();
		List<String> oids = new ArrayList<String>();
		for (int i = 1; i < system.getNumber(); i++) {
			oids.add(system.getOid() + "." + i + ".0");
		}
		for (int i = 1; i <= system.getNumber(); i++) {
			SnmpOperaUtil.snmpGetList(map, ipAddress, community, oids);
		}
		for (Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		system.setMap(map);
		}
	}

	public void buildTcp(String ipAddress, String community) {
		HashMap<String, String> test = new HashMap<String, String>();
		SnmpOperaUtil.snmpGet(test, ipAddress, community, DeviceResourcesOid.sysDesc);
		if (test.get("null") != null && test.get("null").equals("null")) {
		} else {
		tcp = new TcpEntry();
		Map<String, String> map = new HashMap<>();
		List<String> oids = new ArrayList<String>();
		for (int i = 1; i <= tcp.getNumber(); i++) {
			oids.add(tcp.getOid() + "." + i + ".0");
		}
		for (int i = 1; i <= tcp.getNumber(); i++) {
			SnmpOperaUtil.snmpGetList(map, ipAddress, community, oids);
		}
		TcpEntry.TcpTable tcpTable = tcp.new TcpTable();
		Map<String, List<String>> tcpEntry = new HashMap<>();
		Map<String, String> data = new HashMap<>();
		for (int i = 1; i <= tcpTable.getNumber(); i++) {
			SnmpOperaUtil.snmpWalk(data, ipAddress, community, tcpTable.getOid() + "." + i);
			tcpEntry.put(tcpTable.getOid() + "." + i, new ArrayList<String>());
			for (Entry<String, String> result : data.entrySet()) {
				System.out.println("========" + result.getValue());
				tcpEntry.get(tcpTable.getOid() + "." + i).add(result.getValue());
			}
		}
		for (Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		tcpTable.setMap(tcpEntry);
		tcp.setTcpTable(tcpTable);
		tcp.setMap(map);
		}

	}

	public void buildUdp(String ipAddress, String community) {
		HashMap<String, String> test = new HashMap<String, String>();
		SnmpOperaUtil.snmpGet(test, ipAddress, community, DeviceResourcesOid.sysDesc);
		if (test.get("null") != null && test.get("null").equals("null")) {
		} else {
		udp = new UdpEntry();
		UdpEntry.UdpTable udpTable = udp.new UdpTable();
		Map<String, List<String>> udpEntry = new HashMap<>();
		Map<String, String> map = new HashMap<>();
		Map<String, String> data = new HashMap<>();
		for (int i = 1; i <= udpTable.getNumber(); i++) {
			SnmpOperaUtil.snmpWalk(data, ipAddress, community, udpTable.getOid() + "." + i);
			udpEntry.put(udpTable.getOid() + "." + i, new ArrayList<String>());
			for (Entry<String, String> result : data.entrySet()) {
				udpEntry.get(udpTable.getOid() + "." + i).add(result.getValue());
			}
		}
		List<String> oids = new ArrayList<String>();
		for (int i = 1; i <= udp.getNumber(); i++) {
			oids.add(udp.getOid() + "." + i + ".0");
		}
		for (int i = 1; i <= udp.getNumber(); i++) {
			SnmpOperaUtil.snmpGetList(map, ipAddress, community, oids);
		}
		for (Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		udp.setMap(map);
		udpTable.setMap(udpEntry);
		udp.setUdpTable(udpTable);
		}
	}

	public void buildIp(String ipAddress, String community) {
		HashMap<String, String> test = new HashMap<String, String>();
		SnmpOperaUtil.snmpGet(test, ipAddress, community, DeviceResourcesOid.sysDesc);
		if (test.get("null") != null && test.get("null").equals("null")) {
		} else {
		ip = new IpEntry();
		Map<String, String> map = new HashMap<>();
		Map<String, List<String>> ipEntry = new HashMap<>();

		IpEntry.IpAddrTable ipAddrTable = ip.new IpAddrTable();
		Map<String, String> ipAddrTableData = new HashMap<>();
		for (int i = 1; i <= ipAddrTable.getNumber(); i++) {
			SnmpOperaUtil.snmpWalk(ipAddrTableData, ipAddress, community, ipAddrTable.getOid() + "." + i);
			ipEntry.put(ipAddrTable.getOid() + "." + i, new ArrayList<String>());
			for (Entry<String, String> result : ipAddrTableData.entrySet()) {
				ipEntry.get(ipAddrTable.getOid() + "." + i).add(result.getValue());
			}
		}			
		IpEntry.IpNetToMediaTable ipNetToMediaTable = ip.new IpNetToMediaTable();
		Map<String, List<String>> ipNetToMediaTableEntry = new HashMap<>();
		Map<String, String> ipNetToMediaTableData = new HashMap<>();
		for (int i = 1; i <= ipNetToMediaTable.getNumber(); i++) {
			SnmpOperaUtil.snmpWalk(ipNetToMediaTableData, ipAddress, community, ipNetToMediaTable.getOid() + "." + i);
			ipNetToMediaTableEntry.put(ipNetToMediaTable.getOid() + "." + i, new ArrayList<String>());
			for (Entry<String, String> result : ipNetToMediaTableData.entrySet()) {
				ipNetToMediaTableEntry.get(ipNetToMediaTable.getOid() + "." + i).add(result.getValue());
			}
		}
		IpEntry.IpRouteTable ipRouteTable = ip.new IpRouteTable();
		Map<String, String> ipRouteTableData = new HashMap<>();
		for (int i = 1; i <= ipRouteTable.getNumber(); i++) {
			SnmpOperaUtil.snmpWalk(ipRouteTableData, ipAddress, community, ipRouteTable.getOid() + "." + i);
			ipEntry.put(ipRouteTable.getOid() + "." + i, new ArrayList<String>());
			for (Entry<String, String> result : ipRouteTableData.entrySet()) {
				ipEntry.get(ipRouteTable.getOid() + "." + i).add(result.getValue());
			}
		}
		List<String> oids = new ArrayList<String>();
		for (int i = 1; i <= 19; i++) {
			oids.add(ip.getOid() + "." + i + ".0");
		}
		oids.add(ip.getOid() + ".23.0");
		for (int i = 1; i <= 19; i++) {
			SnmpOperaUtil.snmpGetList(map, ipAddress, community, oids);
		}
		for (Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		ip.setMap(map);
		ipAddrTable.setMap(ipAddrTableData);
		ip.setIpAddrTable(ipAddrTable);
		ipNetToMediaTable.setMap(ipNetToMediaTableEntry);
		ip.setIpNetToMediaTable(ipNetToMediaTable);
		ip.setIpRouteTable(ipRouteTable);
		}
	}

	public InterfacesEntry getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(InterfacesEntry interfaces) {
		this.interfaces = interfaces;
	}

	public AtEntry getAt() {
		return at;
	}

	public void setAt(AtEntry at) {
		this.at = at;
	}

	public IcmpEntry getIcmp() {
		return icmp;
	}

	public void setIcmp(IcmpEntry icmp) {
		this.icmp = icmp;
	}

	public IpEntry getIp() {
		return ip;
	}

	public void setIp(IpEntry ip) {
		this.ip = ip;
	}

	public SnmpEntry getSnmp() {
		return snmp;
	}

	public void setSnmp(SnmpEntry snmp) {
		this.snmp = snmp;
	}

	public SystemEntry getSystem() {
		return system;
	}

	public void setSystem(SystemEntry system) {
		this.system = system;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public TcpEntry getTcp() {
		return tcp;
	}

	public void setTcp(TcpEntry tcp) {
		this.tcp = tcp;
	}

	public UdpEntry getUdp() {
		return udp;
	}

	public void setUdp(UdpEntry udp) {
		this.udp = udp;
	}

	public static void main(String args[]) throws JsonGenerationException, JsonMappingException, IOException {
		DeviceResources dr = new DeviceResources();
		dr.buildUdp("10.0.0.1", "public");
		dr.getUdp().getUdpTable().getMap();
	}

	@Override
	public String toString() {
		return "DeviceResources [ipAddress=" + ipAddress + ", interfaces=" + interfaces + ", at=" + at + ", icmp="
				+ icmp + ", ip=" + ip + ", snmp=" + snmp + ", system=" + system + ", tcp=" + tcp + ", udp=" + udp + "]";
	}

}
