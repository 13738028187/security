package com.hzdy.discovery;
/**
* 
* what
* 
* @author kirohuji
* @version 0.1
*/
public class Ip {
	private int id;
	private String ip;
	private String subnetIp;
	private String oId;
	
	public Ip() {
		
	}
	public Ip(String ip) {
		super();
		this.ip = ip;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getSubnetIp() {
		return subnetIp;
	}
	public void setSubnetIp(String subnetIp) {
		this.subnetIp = subnetIp;
	}
	public String getoId() {
		return oId;
	}
	public void setoId(String oId) {
		this.oId = oId;
	}
	
}
