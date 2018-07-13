package com.hzdy.discovery.entity;

import org.snmp4j.smi.IpAddress;

/**
 * @author Administrator
 * 
 * ·�����������Ӷ���
 */
public class ConnectSubnetQuene {
	private int owerId;
	private IpAddress owerIp;
	private int owerInterfaceIndex;
	private ConnectSubnetQuene next;
}
