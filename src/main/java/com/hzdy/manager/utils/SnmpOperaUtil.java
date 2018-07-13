package com.hzdy.manager.utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.snmp4j.CommunityTarget;
import org.snmp4j.Snmp;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;

/**
 * 
 * what
 * 
 * @author
 * @version 0.1
 */
public class SnmpOperaUtil {
	private static String port = "161"; // 监控时使用的端口

	public static void snmpGet(Map<String, String> data, String ipAddress, String community, String oid,
			int snmpVersion) {
		CommunityTarget target = SnmpOperation.createCommunityTarget(ipAddress, port, community, snmpVersion);
		try {
			Snmp snmp = SnmpOperation.createSnmp();
			SnmpOperation.snmpGet(data, snmp, target,oid);
			snmp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void snmpGet(Map<String, String> data, String ipAddress, String community, String oid) {
		CommunityTarget target = SnmpOperation.createDefaultTarget(ipAddress, community);
		try {
			Snmp snmp = SnmpOperation.createSnmp();
			SnmpOperation.snmpGet(data, snmp, target, oid);
			snmp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void snmpGetList(Map<String, String> data, String ipAddress, String community,List<String> oids) {
		CommunityTarget target = SnmpOperation.createDefaultTarget(ipAddress, community);
		try {
			Snmp snmp = SnmpOperation.createSnmp();
			SnmpOperation.snmpGetList(data, snmp, target,oids);
			snmp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void snmpWalk(Map<String, String> datas, String ipAddress, String community,  String soid,
			int snmpVersion) {
		CommunityTarget target = SnmpOperation.createCommunityTarget(ipAddress, port, community, snmpVersion);
		try {
			Snmp snmp = SnmpOperation.createSnmp();
			OID oid = new OID(soid);
			SnmpOperation.snmpWalk(datas, oid, snmp, target, SnmpOperation.ROOT);
			snmp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void snmpWalk(Map<String, String> datas, String ipAddress, String community,  String soid) {
		CommunityTarget target = SnmpOperation.createDefaultTarget(ipAddress, community);
		try {
			Snmp snmp = SnmpOperation.createSnmp();
			OID oid = new OID(soid);
			SnmpOperation.snmpWalk(datas, oid, snmp, target, SnmpOperation.ROOT);
			snmp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void snmpGetBulk(Map<String, String> datas, String ipAddress, String community, String soid) {
		CommunityTarget target = SnmpOperation.createDefaultTarget(ipAddress, community);
		try {
			Snmp snmp = SnmpOperation.createSnmp();
			OID oid = new OID(soid);
			SnmpOperation.snmpGetBulk(datas, oid, snmp, target,soid);
			snmp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void snmpSet(String ipAddress, String community, String soid, Variable value) {
		CommunityTarget target = SnmpOperation.createDefaultTarget(ipAddress, community);
		try {
			Snmp snmp = SnmpOperation.createSnmp();
			OID oid = new OID(soid);
			SnmpOperation.snmpSet(oid, snmp, target, value);
			snmp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
