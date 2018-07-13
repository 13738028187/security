package com.hzdy.manager.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
 * 
 * what
 * 
 * @author
 * @version 0.1
 */
public class SnmpOperation {

	/**
	 * Snmp使用的版本 DEFAULT_VERSION
	 */
	public static final int DEFAULT_VERSION = SnmpConstants.version2c;
	/**
	 * 默认的使用的协议 DEFAULT_PROTOCOL
	 */
	public static final String DEFAULT_PROTOCOL = "udp";
	/**
	 * 默认的端口 DEFAULT_PORT
	 */
	public static final int DEFAULT_PORT = 161;
	/**
	 * 默认的超时时间 DEFAULT_TIMEOUT
	 */
	public static final long DEFAULT_TIMEOUT = 1 * 1000L;
	/**
	 * 默认重试的次数 DEFAULT_RETRY
	 */
	public static final int DEFAULT_RETRY = 1;

	public static final String ROOT = "WALK";

	/**
	 * 创建默认的代理端目标,snmp默认的版本是V2,默认协议udp,默认端口161,默认超时时间3000L,默认重试次数
	 * 
	 * @param ip
	 *            代理端目标
	 * @param community
	 *            社区
	 * @return CommunityTarget
	 */
	public static CommunityTarget createDefaultTarget(String ip, String community) {
		// 设置目标代理端的Ip地址
		Address address = GenericAddress.parse(DEFAULT_PROTOCOL + ":" + ip + "/" + DEFAULT_PORT);
		CommunityTarget target = new CommunityTarget();
		// 设置社区
		target.setCommunity(new OctetString(community));
		target.setAddress(address);
		target.setVersion(DEFAULT_VERSION);
		target.setTimeout(DEFAULT_TIMEOUT); // milliseconds
		target.setRetries(DEFAULT_RETRY);
		// 返回创建好的目标
		return target;
	}

	/**
	 * GET方式 根据OID，获取单条消息,这种方式的特点,它使用的OID,结尾都是.0
	 * 
	 * @param ip
	 *            ip地址
	 * @param community
	 *            社区
	 * @param oid
	 *            值
	 */
	public static void snmpGet(Map<String, String> datas, Snmp snmp, CommunityTarget target, String oid) {
		try {
			// 数据传输采用的格式是PDU,传输的协议一般使用UDP,设置PDU的方式
			PDU pdu = createPDU(PDU.GET);
			// 添加一个变量绑定对象
			if ((oid.toString()).endsWith(".0")) {
				pdu.add(new VariableBinding(new OID(oid)));
			}
			// 最后封装和发送,并将结果赋予respEvent
			ResponseEvent respEvent = snmp.send(pdu, target);
			// 数据反馈的格式也是PDU格式
			PDU response = respEvent.getResponse();
			// 首先判断response结果是否为空
			if (response == null) {
				System.out.println("数据为空");
				datas.put("null", "null");
			}else if (response.getErrorIndex() == PDU.noError && response.getErrorStatus() == PDU.noError) {
				System.out.println("---------");
				Vector<? extends VariableBinding> vector = response.getVariableBindings();
				for(VariableBinding vb : vector){
					System.out.println(vb.getOid() + " = " + vb.getVariable());
					if(vb.getVariable().toString().equals("noSuchObject")){
						datas.put(vb.getOid().toString(), "-1");
					}else{
						datas.put(vb.getOid().toString(), vb.getVariable().toString());
					}
				}
			}

			System.out.println("SNMP GET one OID value finished !");
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
	}

	/**
	 * GET方式 根据OID，获取单条消息,这种方式的特点,它使用的OID,结尾都是.0
	 * 
	 * @param ip
	 *            ip地址
	 * @param community
	 *            社区
	 * @param oid
	 *            值
	 */
	public static void snmpGetList(Map<String, String> datas, Snmp snmp, CommunityTarget target,
			List<String> oids) {
		try {
			// 数据传输采用的格式是PDU,传输的协议一般使用UDP,设置PDU的方式
			PDU pdu = createPDU(PDU.GET);
			// 添加一组变量绑定对象
			for (String oid : oids) {
				if ((oid.toString()).endsWith(".0"))
					pdu.add(new VariableBinding(new OID(oid)));
				else {
					throw new Exception(oid + " 为非基本节点");
				}
			}
			// 最后封装和发送,并将结果赋予respEvent
			ResponseEvent respEvent = snmp.send(pdu, target);
			// 数据反馈的格式也是PDU格式
			PDU response = respEvent.getResponse();
			// 首先判断response结果是否为空
			if (response == null) {
				System.out.println("数据为空");
				datas.put("null", "null");
			} else if (response.getErrorIndex() == PDU.noError && response.getErrorStatus() == PDU.noError) {
				Vector<? extends VariableBinding> vector = response.getVariableBindings();
				for(VariableBinding vb : vector){
					System.out.println(vb.getOid() + " = " + vb.getVariable());
					if(vb.getVariable().toString().equals("noSuchObject")){
						datas.put(vb.getOid().toString(), "-1");
					}else{
						datas.put(vb.getOid().toString(), vb.getVariable().toString());
					}
				}
			}

			System.out.println("SNMP GET one OID value finished !");
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
	}

	/**
	 * GET方式 根据OID，获取单条消息,这种方式的特点,它使用的OID,结尾都是.0
	 * 
	 * @param ip
	 *            ip地址
	 * @param community
	 *            社区
	 * @param oids
	 *            一组值
	 */
	public static void snmpGetList(Map<String, String> datas, String ip, String community, ArrayList<String> oids) {
		// 创建代理端目标
		CommunityTarget target = createDefaultTarget(ip, community);
		// 创建一个SNMP目标
		Snmp snmp = null;
		try {
			// 创建一个SNMP目标
			snmp = createSnmp();
			// 数据传输采用的格式是PDU,传输的协议一般使用UDP,设置PDU的方式
			PDU pdu = createPDU(PDU.GET);
			// 添加一组变量绑定对象
			for (String oid : oids) {
				if ((oid.toString()).endsWith(".0"))
					pdu.add(new VariableBinding(new OID(oid)));
				else {
					throw new Exception(oid + " 为非基本节点");
				}
			}
			// 最后封装和发送,并将结果赋予respEvent
			ResponseEvent respEvent = snmp.send(pdu, target);
			// 数据反馈的格式也是PDU格式
			PDU response = respEvent.getResponse();
			// 首先判断response结果是否为空
			if (response == null) {
				System.out.println("数据为空");
				datas.put("null", "null");
			} else if (response.getErrorIndex() == PDU.noError && response.getErrorStatus() == PDU.noError) {
				Vector<? extends VariableBinding> vector = response.getVariableBindings();
				for(VariableBinding vb : vector){
					System.out.println(vb.getOid() + " = " + vb.getVariable());
					if(vb.getVariable().toString().equals("noSuchObject")){
						datas.put(vb.getOid().toString(), "-1");
					}else{
						datas.put(vb.getOid().toString(), vb.getVariable().toString());
					}
				}
			}

			System.out.println("SNMP GET one OID value finished !");
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
	}

	public static void snmpGetBulkList(Map<String, Map<String, String>> datas, Collection<String> oids, Snmp snmp,
			CommunityTarget target, ArrayList<String> rootOIDs) throws IOException {

		// 数据传输采用的格式是PDU,传输的协议一般使用UDP,设置PDU的方式
		PDU pdu = createPDU(PDU.GETBULK);
		pdu.setMaxRepetitions(10);// 每次返回条数
		pdu.setNonRepeaters(0);// 标量个数
		for (String oid : oids) {// 装配OID对象
			pdu.add(new VariableBinding(new OID(oid)));// 添加oid綁定
		}

		Map<String, String> curr_oids = new HashMap<String, String>();// the next oid
		for (String rootOID : rootOIDs) {// 添加数据收集器
			if (datas.keySet().size() != rootOIDs.size()) {
				datas.put(rootOID, new HashMap<String, String>());
			}
			curr_oids.put(rootOID, rootOID);
		}
		ResponseEvent rspEvt = snmp.send(pdu, target);
		PDU response = rspEvt.getResponse();
		if (response == null) {
			System.out.println("数据为空");
			datas.put("null", new HashMap<String, String>());
		} else if (null != response && response.getErrorIndex() == PDU.noError && response.getErrorStatus() == PDU.noError) {
			// 循环数据桶
			for (VariableBinding variable : response.getVariableBindings()) {
				String key = variable.getOid().toString();
				boolean isEnd = true;// 结束状态标志
				for (String rootOID : rootOIDs) {
					if (key.contains(rootOID)) {// 判断获得的值是否是指定根节点下面
						String value = variable.getVariable().toString();
						datas.get(rootOID).put(key.replace(rootOID, ""), value);
						// System.out.println(key+"="+value);
						if(key.endsWith("noSuchObject")) {
							curr_oids.put(rootOID, "-1");
						}else {
							curr_oids.put(rootOID, key);
						}
						isEnd = false;
					}
				}
				if (isEnd) {
					return;
				}
			}
			if (curr_oids.isEmpty()) {
				return;
			}
			snmpGetBulkList(datas, curr_oids.values(), snmp, target, rootOIDs);
		}
	}

	public static void snmpGetBulk(Map<String, String> datas, OID oid, Snmp snmp, CommunityTarget target,
			String rootOID) throws IOException {
		// 数据传输采用的格式是PDU,传输的协议一般使用UDP,设置PDU的方式
		PDU pdu = createPDU(PDU.GETBULK);
		pdu.setMaxRepetitions(10);// 每次返回条数
		// request.setNonRepeaters(2);// 標量个数 //標量在前 矢量在后
		pdu.add(new VariableBinding(oid));
		ResponseEvent rspEvt = snmp.send(pdu, target);
		PDU response = rspEvt.getResponse();
		if (response == null) {
			System.out.println("数据为空");
			datas.put("null", "null");
		} else	if (null != response && response.getErrorIndex() == PDU.noError && response.getErrorStatus() == PDU.noError) {
			OID curr_oid = null;
			// 循环数据桶
			for (VariableBinding variable : response.getVariableBindings()) {
				String syn = variable.getVariable().getSyntaxString();//节点数据类型
				String key = variable.getOid().toString();
				if (key.contains(rootOID)) {// 判断获得的值是否是指定根节点下面
					
					String value = variable.getVariable().toString();
					if(value.endsWith("noSuchObject")) {
						datas.put(rootOID.concat(key.replace(rootOID, "")), "-1");
					}else {
						datas.put(rootOID.concat(key.replace(rootOID, "")), value);
					}
					datas.put(rootOID.concat(key.replace(rootOID, "")), value);
					//System.out.println(key.replace(rootOID, ""));
					curr_oid = variable.getOid();
				} else {
					return;
				}
			}
			if (null == curr_oid) {
				return;
			}
			snmpGetBulk(datas, curr_oid, snmp, target, rootOID);
		}
	}

	public static void snmpWalk(List<String> indexs, OID oid, Snmp snmp, CommunityTarget target, String rootOID)
			throws IOException {
		PDU pdu = createPDU(PDU.GETBULK);
		pdu.setMaxRepetitions(10);
		pdu.add(new VariableBinding(oid));
		ResponseEvent rspEvt = snmp.send(pdu, target);
		PDU response = rspEvt.getResponse();
		if (response == null) {
			System.out.println("数据为空");
			indexs.add ("null");
		} else if (null != response && response.getErrorIndex() == PDU.noError && response.getErrorStatus() == PDU.noError) {
			OID curr_oid = null;
			for (VariableBinding variable : response.getVariableBindings()) {
				String curr_str = variable.getOid().toString();
				if (curr_str.contains(rootOID)) {// 判断获得的值是否是指定根节点下面
					//String index = SnmpUtil.ascllToMac(curr_str.replace(rootOID, ""));
					String index = curr_str.replace(rootOID, "");
					indexs.add(index);
					System.out.println(index);
					curr_oid = variable.getOid();
				} else {
					return;
				}
			}
			if (null == curr_oid) {
				return;
			}
			snmpWalk(indexs, curr_oid, snmp, target, rootOID);
		}
	}

	public static void snmpWalk(Map<String, String> datas, OID oid, Snmp snmp, CommunityTarget target, String type)
			throws Exception {
		if (type.equals(SnmpOperation.ROOT))
			type = oid.toString();
		String curr_str=type;
		OID curr_oid = new OID(curr_str);
		boolean flag=true;
		while(flag) {
			PDU pdu = createPDU(PDU.GETNEXT);
			pdu.add(new VariableBinding(curr_oid));
			ResponseEvent rspEvt = snmp.send(pdu, target);
			PDU response = rspEvt.getResponse();
			if (response == null) {
				System.out.println("数据为空");
				datas.put("null", "null");
			} else	if (response!=null&&response.getErrorIndex() == PDU.noError && response.getErrorStatus() == PDU.noError) {
				VariableBinding vb = (VariableBinding) response.getVariableBindings().firstElement();
				curr_oid = vb.getOid();
				curr_str = curr_oid.toString();
				if (curr_str.contains(type)) {// 判断获得的值是否是指定根节点下面
					String key = vb.getOid().toString();
					if( vb.getVariable().toString().endsWith("noSuchObject")) {
						datas.put(key.replace(type, ""), "-1");
					}else {
						datas.put(key.replace(type, ""), vb.getVariable().toString());
					}
					System.out.println(key);
			//		snmpWalk(datas, curr_oid, snmp, target, type);
				}else {
					flag=false;
				}
			} else {
				throw new Exception("错误信息:" + response.getErrorStatusText());
			}
		}
	
		
	/*	PDU pdu = createPDU(PDU.GETNEXT);
		pdu.add(new VariableBinding(oid));
		ResponseEvent rspEvt = snmp.send(pdu, target);
		PDU response = rspEvt.getResponse();
		if (response.getErrorIndex() == PDU.noError && response.getErrorStatus() == PDU.noError) {
			VariableBinding vb = (VariableBinding) response.getVariableBindings().firstElement();
			OID curr_oid = vb.getOid();
			String curr_str = curr_oid.toString();
			if (curr_str.contains(type)) {// 判断获得的值是否是指定根节点下面
				String key = vb.getOid().toString();
				datas.put(key.replace(type, ""), vb.getVariable().toString());
				System.out.println(key);
				snmpWalk(datas, curr_oid, snmp, target, type);
			}
		} else {
			throw new Exception("错误信息:" + response.getErrorStatusText());
		}*/
	}

	public static void snmpSet(OID oid, Snmp snmp, CommunityTarget target, Variable newVar) throws Exception {
		PDU request = createPDU(PDU.SET);
		request.add(new VariableBinding(oid, newVar));
		ResponseEvent resEvt = snmp.send(request, target);
		PDU response = resEvt.getResponse();
		if (response.getErrorIndex() == PDU.noError && response.getErrorStatus() == PDU.noError) {// 判断返回报文是否正确
			VariableBinding vb = (VariableBinding) response.getVariableBindings().firstElement();
			Variable var = vb.getVariable();
			if (var.equals(newVar)) {// 比较返回值和设置值
				System.out.println("SET操作成功 ！");
			} else {
				System.out.println("SET操作失败 ！");
			}
		} else {
			throw new Exception("错误信息:" + response.getErrorStatusText());
		}
	}

	public static Snmp createSnmp() throws IOException {
		TransportMapping transport = new DefaultUdpTransportMapping();
		Snmp snmp = new Snmp(transport);
		snmp.listen();// 启动一个UDP通信线程
		return snmp;
	}

	public static CommunityTarget createCommunityTarget(String host, String port, String community, int version) {
		CommunityTarget target = new CommunityTarget();
		target.setAddress(new UdpAddress(host + "/" + port));// 设置Snmp Agent的访问地址和端口
		target.setCommunity(new OctetString(community));// 设置共同体,默认是"public"
		target.setRetries(3);// 设置重试次数
		target.setTimeout(3000);// 设置超时毫秒数
		target.setVersion(version);// 设置snmp的版本号
		return target;
	}

	private static PDU createPDU(int type) {
		PDU pdu = new PDU();
		pdu.setType(type);
		return pdu;
	}

}
