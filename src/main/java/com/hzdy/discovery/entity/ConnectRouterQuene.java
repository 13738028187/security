package com.hzdy.discovery.entity;

import org.snmp4j.smi.IpAddress;

/**
 * @author Administrator
 * 
 * ·�����Ӷ���
 */
public class ConnectRouterQuene {
	private int fromId;
	private IpAddress fromIp;
	private int fromInterfaceIndex;
	private int toId;
	private IpAddress toIp;
	private int toInterfaceIndex;
	private ConnectRouterQuene next;
}
