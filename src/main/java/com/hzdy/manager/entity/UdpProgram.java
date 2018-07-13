package com.hzdy.manager.entity;

public class UdpProgram {
	private String port;
	private String ip;
	

	public UdpProgram() {
		super();
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "UdpProgram [port=" + port + ", ip=" + ip + "]";
	}
	
}
