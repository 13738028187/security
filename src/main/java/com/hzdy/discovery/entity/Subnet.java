package com.hzdy.discovery.entity;

import java.util.ArrayList;

import org.snmp4j.smi.IpAddress;

/**
 * @author Administrator
 * 
 * ����
 */
public class Subnet {
	private IpAddress subnetAddress;
	private IpAddress subnetMask;
	private ArrayList<IpAddress> interfaceList;
}
