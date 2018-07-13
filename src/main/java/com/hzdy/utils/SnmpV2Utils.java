package com.hzdy.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpV2Utils {
	private static SnmpV2Utils snmpUtils;
	private final int DEFAULT_VERSION = SnmpConstants.version2c;
	private final String DEFAULT_PROTOCOL = "udp";
	private final int DEFAULT_PORT = 161;
	private final long DEFAULT_TIMEOUT = 2 * 1000L;
	private final int DEFAULT_RETRY = 1;

	public static SnmpV2Utils getInstance() {
		if (snmpUtils == null) {
			return snmpUtils = new SnmpV2Utils();
		} else {
			return snmpUtils;
		}
	}
    public static void main(String args[]) {
    	
    	System.out.println(SnmpV2Utils.getInstance().Get("192.168.1.3","public","1.3.6.1.2.1.1.7.0"));
    }
	public CommunityTarget createDefaultTarget(String ip, String community) {
		Address address = GenericAddress.parse(DEFAULT_PROTOCOL + ":" + ip + "/" + DEFAULT_PORT);
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(community));
		target.setAddress(address);
		target.setVersion(DEFAULT_VERSION);
		target.setTimeout(DEFAULT_TIMEOUT); // milliseconds
		target.setRetries(DEFAULT_RETRY);
		return target;
	}

	public List<VariableBinding> Get(String ip, String community, String oid) {
		List<VariableBinding> result = new ArrayList<VariableBinding>();
		CommunityTarget target = createDefaultTarget(ip, community);
		Snmp snmp = null;
		try {
			PDU pdu = new PDU();
			if ((oid.toString()).endsWith(".0"))
				pdu.add(new VariableBinding(new OID(oid)));
			else
				throw new Exception(oid + "�ǻ����ڵ�");
			DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
			snmp = new Snmp(transport);
			snmp.listen();
			pdu.setType(PDU.GET);
			ResponseEvent respEvent = snmp.send(pdu, target);
			PDU response = respEvent.getResponse();
			if (response == null) {
				System.out.println("response is null, request time out");
				return null;
			} else {
				System.out.println("response pdu size is " + response.size());
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < response.size(); i++) {
					VariableBinding vb = response.get(i);
					sb.append(vb.getOid().toString() + ":" + vb.getVariable().toString());
					System.out.println(vb.getOid() + " = " + vb.getVariable());
					result.add(response.get(i));
				}
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SNMP Get Exception:" + e);
			return null;
		} finally {
			if (snmp != null) {
				try {
					snmp.close();
				} catch (IOException ex1) {
					snmp = null;
				}
			}

		}
	}
	public List<VariableBinding> GetNext(String ip, String community, String oid) {
		List<VariableBinding> result = new ArrayList<VariableBinding>();
		CommunityTarget target = createDefaultTarget(ip, community);
		Snmp snmp = null;
		try {
			PDU pdu = new PDU();
			DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
			snmp = new Snmp(transport);
			snmp.listen();
			pdu.setType(PDU.GETBULK);
			ResponseEvent respEvent = snmp.send(pdu, target);
			PDU response = respEvent.getResponse();
			if (response == null) {
				System.out.println("response is null, request time out");
				return null;
			} else {
				System.out.println("response pdu size is " + response.size());
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < response.size(); i++) {
					VariableBinding vb = response.get(i);
					sb.append(vb.getOid().toString() + ":" + vb.getVariable().toString());
					System.out.println(vb.getOid() + " = " + vb.getVariable());
					result.add(response.get(i));
				}
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SNMP Get Exception:" + e);
			return null;
		} finally {
			if (snmp != null) {
				try {
					snmp.close();
				} catch (IOException ex1) {
					snmp = null;
				}
			}

		}
	}
	public List<VariableBinding> Get(String ip, String community, String[] oids) {
		List<VariableBinding> result = new ArrayList<VariableBinding>();
		CommunityTarget target = createDefaultTarget(ip, community);
		Snmp snmp = null;
		try {
			PDU pdu = new PDU();
			for (String oid : oids) {
				if ((oid.toString()).endsWith(".0"))
					pdu.add(new VariableBinding(new OID(oid)));
				else
					throw new Exception(oid + "Ϊ�ǻ����ڵ�");
			}
			DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
			snmp = new Snmp(transport);
			snmp.listen();
			pdu.setType(PDU.GET);
			ResponseEvent respEvent = snmp.send(pdu, target);
			PDU response = respEvent.getResponse();
			if (response.getErrorIndex() == PDU.noError && response.getErrorStatus() == PDU.noError) {
				System.out.println("response pdu size is " + response.size());
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < response.size(); i++) {
					VariableBinding vb = response.get(i);
					sb.append(vb.getOid().toString() + ":" + vb.getVariable().toString());
					System.out.println(vb.getOid() + " = " + vb.getVariable());
					result.add(response.get(i));
				}
				return result;
			} else {
				System.out.println("response is null, request time out");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SNMP Get Exception:" + e);
			return null;
		} finally {
			if (snmp != null) {
				try {
					snmp.close();
				} catch (IOException ex1) {
					snmp = null;
				}
			}

		}
	}
	public List<VariableBinding> Walk(String ip, String community, String targetOid) {
		List<VariableBinding> result = new ArrayList<VariableBinding>();
		CommunityTarget target = createDefaultTarget(ip, community);
		TransportMapping transport = null;
		Snmp snmp = null;
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
		            break;  
		          } else {  
		            vb = response.get(0);  
		          }  
				finished = checkWalkFinished(targetOID, pdu, vb);
				if (!finished) {
					pdu.setRequestID(new Integer32(0));
					pdu.set(0, vb);
					result.add(vb);
					System.out.println(vb.getOid() + " = " + vb.getVariable());
				} else {
					System.out.println("SNMP walk OID has finished.");
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
			System.out.println("[true] targetOID.leftMostCompare() != 0");
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

}
