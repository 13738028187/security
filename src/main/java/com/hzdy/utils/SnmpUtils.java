package com.hzdy.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.Null;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import com.hzdy.discovery.Host;
import com.hzdy.discovery.entity.Switch;
import com.hzdy.discovery.oid.code.SNMPCODE;


public class SnmpUtils {
	private static SnmpUtils snmpUtils;
	private final int DEFAULT_VERSION = SnmpConstants.version2c;
	private final String DEFAULT_PROTOCOL = "udp";
	private final int DEFAULT_PORT = 161;
	private final long DEFAULT_TIMEOUT = 1 * 1000L;
	private final int DEFAULT_RETRY = 1;

	public static SnmpUtils getInstance() {
		if (snmpUtils == null) {
			return snmpUtils = new SnmpUtils();
		} else {
			return snmpUtils;
		}
	}

	public CommunityTarget createDefault(String ip, String community) {
		Address address = GenericAddress.parse(DEFAULT_PROTOCOL + ":" + ip + "/" + DEFAULT_PORT);
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(community));
		target.setAddress(address);
		target.setVersion(DEFAULT_VERSION);
		target.setTimeout(DEFAULT_TIMEOUT); // milliseconds
		target.setRetries(DEFAULT_RETRY);
		return target;
	}

	public String Get(String ip, String community, String oid) {
		CommunityTarget target = createDefault(ip, community);
		Snmp snmp = null;
		try {
			PDU pdu = new PDU();
			pdu.add(new VariableBinding(new OID(oid)));
			DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
			snmp = new Snmp(transport);
			snmp.listen();
			pdu.setType(PDU.GET);
			ResponseEvent respEvent = snmp.send(pdu, target);
			PDU response = respEvent.getResponse();
			if (response == null) {
				System.out.println("response is null, request time out");
				return "none";
			} else {
				System.out.println("response pdu size is " + response.size());
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < response.size(); i++) {
					VariableBinding vb = response.get(i);
					sb.append(vb.getOid().toString() + ":" + vb.getVariable().toString());
					System.out.println(vb.getOid() + " = " + vb.getVariable());
				}
				return sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SNMP Get Exception:" + e);
		} finally {
			if (snmp != null) {
				try {
					snmp.close();
				} catch (IOException ex1) {
					snmp = null;
				}
			}

		}
		return "none";
	}

	/* ����targetOID����ȡ�������� */
	public Map<Integer, String> WalkOld(String ip, String community, String targetOid) {
		CommunityTarget target = createDefault(ip, community);
		TransportMapping transport = null;
		Snmp snmp = null;
		Map<Integer, String> result = new HashMap<>();
		try {
			transport = new DefaultUdpTransportMapping();
			snmp = new Snmp(transport);
			transport.listen();

			PDU pdu = new PDU();
			OID targetOID = new OID(targetOid);
			pdu.add(new VariableBinding(targetOID));
			boolean finished = false;
			int counter = 0;
			while (!finished) {
				VariableBinding vb = null;
				ResponseEvent respEvent = snmp.getNext(pdu, target);
				PDU response = respEvent.getResponse();
				if (null == response) {
					System.out.println("responsePDU == null");
					finished = true;
					result.put(counter, "none");
					break;
				} else {
					vb = response.get(0);
				}
				finished = checkWalkFinished(targetOID, pdu, vb);
				if (!finished) {
				/*	System.out.println("==== walk each vlaue :");
					System.out.println(vb.getOid() + " = " + vb.getVariable());*/
					result.put(counter, vb.getOid().toString());
					// Set up the variable binding for the next entry.
					pdu.setRequestID(new Integer32(0));
					pdu.set(0, vb);
				} else {
					/*System.out.println("SNMP walk OID has finished.");*/
					snmp.close();
				}
				counter++;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SNMP walk Exception: " + e);
		} finally {
			if (snmp != null) {
				try {
					snmp.close();
				} catch (IOException ex1) {
					snmp = null;
				}
			}
		}
		return result;
	}

	/* ����targetOID����ȡ�������� */
	public Map<Integer, String> Walk(String ip, String community, String targetOid) {
		CommunityTarget target = createDefault(ip, community);
		TransportMapping transport = null;
		Snmp snmp = null;
		Map<Integer, String> result = new HashMap<>();
		try {
			transport = new DefaultUdpTransportMapping();
			snmp = new Snmp(transport);
			transport.listen();

			PDU pdu = new PDU();
			OID targetOID = new OID(targetOid);
			pdu.add(new VariableBinding(targetOID));
			boolean finished = false;
			int counter = 0;
			while (!finished) {
				VariableBinding vb = null;
				ResponseEvent respEvent = snmp.getNext(pdu, target);
				PDU response = respEvent.getResponse();
				if (null == response) {
					System.out.println("responsePDU === null");
					finished = true;
					result.put(counter, "none");
					break;
				} else {
					vb = response.get(0);
				}
				finished = checkWalkFinished(targetOID, pdu, vb);
				if (!finished) {
				/*	System.out.println("==== walk each vlaue :");
					System.out.println(vb.getOid() + " = " + vb.getVariable());*/
					result.put(counter, vb.getVariable().toString());
					// Set up the variable binding for the next entry.
					pdu.setRequestID(new Integer32(0));
					pdu.set(0, vb);
				} else {
					/*System.out.println("SNMP walk OID has finished.");*/
					snmp.close();
				}
				counter++;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SNMP walk Exception: " + e);
		} finally {
			if (snmp != null) {
				try {
					snmp.close();
				} catch (IOException ex1) {
					snmp = null;
				}
			}
		}
		return result;
	}

	private boolean checkWalkFinished(OID targetOID, PDU pdu, VariableBinding vb) {
		boolean finished = false;
		if (pdu.getErrorStatus() != 0) {
			/*System.out.println("[true] responsePDU.getErrorStatus() != 0 ");*/
			System.out.println(pdu.getErrorStatusText());
			finished = true;
		} else if (vb.getOid() == null) {
			System.out.println("[true] vb.getOid() == null");
			finished = true;
		} else if (vb.getOid().size() < targetOID.size()) {
			System.out.println("[true] vb.getOid().size() < targetOID.size()");
			finished = true;
		} else if (targetOID.leftMostCompare(targetOID.size(), vb.getOid()) != 0) {
			/*System.out.println("[true] targetOID.leftMostCompare() != 0");*/
			finished = true;
		} else if (Null.isExceptionSyntax(vb.getVariable().getSyntax())) {
			System.out.println("[true] Null.isExceptionSyntax(vb.getVariable().getSyntax())");
			finished = true;
		} else if (vb.getOid().compareTo(targetOID) <= 0) {
			System.out.println("[true] Variable received is not " + "lexicographic successor of requested " + "one:");
			System.out.println(vb.toString() + " <= " + targetOID);
			finished = true;
		}
		return finished;
	}

	public Switch snmpBulkFdbTable(Host host) {
		List<String> switchLearnedMac = new ArrayList<>();
		List<Integer> switchMacPort = new ArrayList<>();
		String lastOid = null;
		Snmp snmp = null;

		try {
			snmp = new Snmp(new DefaultUdpTransportMapping());
			snmp.listen();

			CommunityTarget target = new CommunityTarget();
			target.setCommunity(new OctetString("public"));
			target.setVersion(SnmpConstants.version2c);
			target.setAddress(new UdpAddress(host.getIpAddress() + "/161"));
			target.setTimeout(3000); // 3s
			target.setRetries(2);

			PDU pdu = new PDU();
			pdu.setType(PDU.GETBULK);
			pdu.setMaxRepetitions(200);
			pdu.setNonRepeaters(0);

			pdu.add(new VariableBinding(new OID(SNMPCODE.DOT1DTPFDBTABLE)));
			ResponseEvent responseEvent = snmp.send(pdu, target);
			PDU response = responseEvent.getResponse();

			if (response == null) {
				System.out.println("[SNMPTool_snmpBulkFdbTable] TimeOut... targetIp:" + host.getIpAddress());
			} else {
				if (response.getErrorStatus() == PDU.noError) {
					Vector<? extends VariableBinding> vbs = response.getVariableBindings();
					for (VariableBinding vb : vbs) {
						int dataType = vb.getOid().get(12);
						// dot1qTpFdbAddress
						if (dataType == 1) {

							switchLearnedMac.add(vb.getVariable().toString());
						}
						// dot1qTpFdbPort
						else if (vb.getOid().get(12) == 2) {
							switchMacPort.add(vb.getVariable().toInt());
						}
						// arrive dot1qTpFdbStatus return
						else {
							return new Switch(host, switchLearnedMac, switchMacPort);
						}
						// System.out.println("oid:"+vb.getOid()+" varilable:"+vb.getVariable());
					}
					lastOid = vbs.get(vbs.size() - 1).getOid().toString();
				} else {
					System.out.println("[SNMPTool_snmpBulkFdbTable] targetIp" + host.getIpAddress() + " Error:"
							+ response.getErrorStatusText());
				}
			}
			snmp.close();
		} catch (IOException e) {
			System.out.println("[SNMPTool_snmpBulkFdbTable] snmpBulkFdbTable targetIp " + host.getIpAddress()
					+ " IOException " + e.getMessage());
		}
		return new Switch(host, switchLearnedMac, switchMacPort);
	}
}
