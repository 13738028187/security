package com.hzdy.manager.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hzdy.manager.entity.DeviceActualResources;
import com.hzdy.manager.entity.DeviceStaticResources;

/**
 * 
 * what
 * 
 * @author
 * @version 0.1
 */
public class DeviceResourcesBuilder {
	public static DeviceActualResources deviceActualResourcesBuild(String ipAddress, String community) {
		HashMap<String, String> data = new HashMap<String, String>();
		SnmpOperaUtil.snmpGet(data, ipAddress, community, DeviceResourcesOid.sysDesc);
		if (data.get("null") != null && data.get("null").equals("null")) {
			return null;
		} else {
			data.clear();
			DeviceActualResources dar = new DeviceActualResources();
			String soid = DeviceResourcesOid.hrSWRunName;
			/*
			 * HashMap<String,String> hrSWRunNameMapResult=new HashMap<String,String>();
			 * List<String> hrSWRunNameListResult=new ArrayList<String>();
			 * SnmpOperaUtil.snmpWalk(hrSWRunNameMapResult, ipAddress, community, soid);
			 * for(Entry<String,String> entry:hrSWRunNameMapResult.entrySet()) {
			 * hrSWRunNameListResult.add(entry.getValue()); }
			 * dar.setHrSWRunName(hrSWRunNameListResult);
			 */

			HashMap<String, String> sysNameDate = new HashMap<String, String>();
			SnmpOperaUtil.snmpGet(sysNameDate, ipAddress, community, DeviceResourcesOid.sysName);
			dar.setIp(ipAddress);
			dar.setSysName(sysNameDate.get(DeviceResourcesOid.sysName));
			HashMap<String, String> hrProcessorLoadAvgMapResult = new HashMap<String, String>();
			soid = DeviceResourcesOid.hrProcessorLoadAvg;
			SnmpOperaUtil.snmpWalk(hrProcessorLoadAvgMapResult, ipAddress, community, soid);
			int hrProcessorLoadAvgStringResult = 0;
			for (Entry<String, String> entry : hrProcessorLoadAvgMapResult.entrySet()) {
				hrProcessorLoadAvgStringResult += Integer.valueOf(entry.getValue());
			}
			dar.setHrProcessorLoadAvg(hrProcessorLoadAvgStringResult / hrProcessorLoadAvgMapResult.size());

			HashMap<String, String> ifInUcastPktsMapResult = new HashMap<String, String>();
			List<String> ifInUcastPktsListResult = new ArrayList<String>();
			soid = DeviceResourcesOid.ifInUcastPkts;
			SnmpOperaUtil.snmpWalk(ifInUcastPktsMapResult, ipAddress, community, soid);
			for (Entry<String, String> entry : ifInUcastPktsMapResult.entrySet()) {
				ifInUcastPktsListResult.add(entry.getValue());
			}
			dar.setIfInUcastPkts(ifInUcastPktsListResult);

			HashMap<String, String> ifMTUMapResult = new HashMap<String, String>();
			List<String> ifMTUListResult = new ArrayList<String>();
			soid = DeviceResourcesOid.ifMTU;
			SnmpOperaUtil.snmpWalk(ifMTUMapResult, ipAddress, community, soid);
			for (Entry<String, String> entry : ifMTUMapResult.entrySet()) {
				ifMTUListResult.add(entry.getValue());
			}
			dar.setIfMTU(ifMTUListResult);

			HashMap<String, String> ifOperStatusMapResult = new HashMap<String, String>();
			List<String> ifOperStatusListResult = new ArrayList<String>();
			soid = DeviceResourcesOid.ifOperStatus;
			SnmpOperaUtil.snmpWalk(ifOperStatusMapResult, ipAddress, community, soid);
			for (Entry<String, String> entry : ifOperStatusMapResult.entrySet()) {
				ifOperStatusListResult.add(entry.getValue());
			}
			dar.setIfOperStatus(ifOperStatusListResult);

			HashMap<String, String> ifOutOctetMapResult = new HashMap<String, String>();
			List<String> ifOutOctetListResult = new ArrayList<String>();
			soid = DeviceResourcesOid.ifOutOctet;
			SnmpOperaUtil.snmpWalk(ifOutOctetMapResult, ipAddress, community, soid);
			for (Entry<String, String> entry : ifOutOctetMapResult.entrySet()) {
				ifOutOctetListResult.add(entry.getValue());
			}
			dar.setIfOutOctet(ifOutOctetListResult);

			HashMap<String, String> ifOutUcastPktsMapResult = new HashMap<String, String>();
			List<String> ifOutUcastPktsListResult = new ArrayList<String>();
			soid = DeviceResourcesOid.ifOutUcastPkts;
			SnmpOperaUtil.snmpWalk(ifOutUcastPktsMapResult, ipAddress, community, soid);
			for (Entry<String, String> entry : ifOutUcastPktsMapResult.entrySet()) {
				ifOutUcastPktsListResult.add(entry.getValue());
			}
			dar.setIfOutUcastPkts(ifOutUcastPktsListResult);

			HashMap<String, String> ifSpeedMapResult = new HashMap<String, String>();
			List<String> ifSpeedListResult = new ArrayList<String>();
			soid = DeviceResourcesOid.ifSpeed;
			SnmpOperaUtil.snmpWalk(ifSpeedMapResult, ipAddress, community, soid);
			for (Entry<String, String> entry : ifSpeedMapResult.entrySet()) {
				ifSpeedListResult.add(entry.getValue());
			}
			dar.setIfSpeed(ifSpeedListResult);

			HashMap<String, String> ifInOctetMapResult = new HashMap<String, String>();
			List<String> ifInOctetListResult = new ArrayList<String>();
			soid = DeviceResourcesOid.ifInOctet;
			SnmpOperaUtil.snmpWalk(ifInOctetMapResult, ipAddress, community, soid);
			for (Entry<String, String> entry : ifInOctetMapResult.entrySet()) {
				ifInOctetListResult.add(entry.getValue());
			}
			dar.setIfInOctet(ifInOctetListResult);

			HashMap<String, String> memAvailRealResultResult = new HashMap<String, String>();
			soid = DeviceResourcesOid.memAvailReal;
			SnmpOperaUtil.snmpGet(memAvailRealResultResult, ipAddress, community, soid);
			dar.setMemAvailReal(Double.valueOf(memAvailRealResultResult.get(DeviceResourcesOid.memAvailReal)));
			HashMap<String, String> memTotalResultResult = new HashMap<String, String>();
			soid = DeviceResourcesOid.hrMemorySize;
			SnmpOperaUtil.snmpGet(memTotalResultResult, ipAddress, community, soid);
			dar.setMemAvailReal(1.0-Double.valueOf(
					dar.getMemAvailReal() / Double.valueOf(memTotalResultResult.get(DeviceResourcesOid.hrMemorySize))));

			/*
			 * dsr.setIfDescr(ifDescr); dsr.setIfInOctet(ifInOctet);
			 * dsr.setIfInUcastPkts(ifInUcastPkts); dsr.setIfMTU(ifMTU);
			 * dsr.setIfNumber(ifNumber); dsr.setIfOperStatus(ifOperStatus);
			 * dsr.setIfOutOctet(ifOutOctet); dsr.setIfOutUcastPkts(ifOutUcastPkts);
			 * dsr.setIfPhysAddress(ifPhysAddress); dsr.setIfSpeed(ifSpeed);
			 * dsr.setIfType(ifType);
			 */
			return dar;
		}
	}

	public static DeviceStaticResources deviceStaticResourcesBuild(String ipAddress, String community) {
		System.out.println(ipAddress);
		HashMap<String, String> data = new HashMap<String, String>();
		SnmpOperaUtil.snmpGet(data, ipAddress, community, DeviceResourcesOid.sysDesc);
		if (data.get("null") != null && data.get("null").equals("null")) {
			return null;
		} else {
			data.clear();
			DeviceStaticResources dsr = new DeviceStaticResources();
			ArrayList<String> oids = new ArrayList<String>();
			oids.add(DeviceResourcesOid.sysDesc);
			oids.add(DeviceResourcesOid.sysContact);
			oids.add(DeviceResourcesOid.sysLocation);
			oids.add(DeviceResourcesOid.sysName);
			oids.add(DeviceResourcesOid.sysService);
			oids.add(DeviceResourcesOid.sysUptime);
			oids.add(DeviceResourcesOid.hrMemorySize);
			oids.add(DeviceResourcesOid.ifNumber);
			SnmpOperaUtil.snmpGetList(data, ipAddress, community, oids);
			// HashMap<String,String> hrSWInstalledNameMapResult=new
			// HashMap<String,String>();
			// SnmpOperaUtil.snmpWalk(hrSWInstalledNameMapResult, ipAddress, community,
			// DeviceResourcesOid.hrSWInstalledName);
			dsr.setSysContact(data.get(DeviceResourcesOid.sysContact));
			dsr.setSysDesc(data.get(DeviceResourcesOid.sysDesc));
			dsr.setSysLocation(data.get(DeviceResourcesOid.sysLocation));
			dsr.setSysName(data.get(DeviceResourcesOid.sysName));
			dsr.setSysService(data.get(DeviceResourcesOid.sysService));
			dsr.setSysUptime(data.get(DeviceResourcesOid.sysUptime));
			dsr.setHrMemorySize(data.get(DeviceResourcesOid.hrMemorySize));
			dsr.setIfNumber(data.get(DeviceResourcesOid.ifNumber));
			List<String> hrSWInstalledNameListResult = new ArrayList<String>();
			/*
			 * for(Entry<String,String> entry:hrSWInstalledNameMapResult.entrySet()) {
			 * hrSWInstalledNameListResult.add(entry.getValue()); }
			 * dsr.setHrSWInstalledName(hrSWInstalledNameListResult);
			 */

			HashMap<String, String> ifDescrMapResult = new HashMap<String, String>();
			List<String> ifDescrListResult = new ArrayList<String>();
			String soid = DeviceResourcesOid.ifDescr;
			SnmpOperaUtil.snmpWalk(ifDescrMapResult, ipAddress, community, soid);
			for (Entry<String, String> entry : ifDescrMapResult.entrySet()) {
				ifDescrListResult.add(entry.getValue());
			}
			dsr.setIfDescr(ifDescrListResult);

			HashMap<String, String> ifPhysAddressMapResult = new HashMap<String, String>();
			List<String> ifPhysAddressListResult = new ArrayList<String>();
			soid = DeviceResourcesOid.ifPhysAddress;
			SnmpOperaUtil.snmpWalk(ifPhysAddressMapResult, ipAddress, community, soid);
			for (Entry<String, String> entry : ifPhysAddressMapResult.entrySet()) {
				ifPhysAddressListResult.add(entry.getValue());
			}
			dsr.setIfPhysAddress(ifPhysAddressListResult);

			HashMap<String, String> ifTypeMapResult = new HashMap<String, String>();
			List<String> ifTypeListResult = new ArrayList<String>();
			soid = DeviceResourcesOid.ifType;
			SnmpOperaUtil.snmpWalk(ifTypeMapResult, ipAddress, community, soid);
			for (Entry<String, String> entry : ifTypeMapResult.entrySet()) {
				ifTypeListResult.add(entry.getValue());
			}
			dsr.setIfType(ifTypeListResult);
			return dsr;
		}

	}

}
