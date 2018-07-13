package com.hzdy.discovery;

import java.util.ArrayList;

/**
 * 设备
 * @author kirohuji
 *
 */
public class Device {
	private int deviceId;
	private String name;
	private int type;
	private String netLocation;
	private String protocol;
	private String status;
	private String mode;
	private int ipId;
	private int onlineDate;
	public Device(String name, int type, String netLocation, String protocol, String status) {
		super();
		this.name = name;
		this.type = type;
		this.netLocation = netLocation;
		this.protocol = protocol;
		this.status = status;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		deviceId = deviceId;
	}

	public String getNetLocation() {
		return netLocation;
	}

	public void setNetLocation(String netLocation) {
		this.netLocation = netLocation;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/* private ArrayList<String> interfaces; */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	/*
	 * public ArrayList<String> getInterfaces() { return interfaces; } public void
	 * setInterfaces(ArrayList<String> interfaces) { this.interfaces = interfaces; }
	 * public boolean addInterface(String i){ interfaces.add(i); return true; }
	 * public boolean removeInterface(String i) { int index=-1; for(int
	 * j=0;j<interfaces.size();j++) { if(interfaces.get(j).equals(i)) { index=j; } }
	 * if(index==-1) { System.out.println("此设备的接口没有此ip地址"); return false; }
	 * interfaces.remove(index); return true; }
	 */
}
