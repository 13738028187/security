package com.hzdy.manager.entity;

public class TcpProgram {
	private String tcpConnState;// 这个TCP连接的状态
	private String tcpConnLocalAddress;// 此TCP连接的本地IP地址。
	private String tcpConnLocalPort;//此TCP连接的本地端口号
	private String tcpConnRemAddress;// 此TCP连接的远程IP地址
	private String tcpConnRemPort;// 此TCP连接的远程端口号
	
	public TcpProgram() {
		super();
	}
	public String getTcpConnState() {
		return tcpConnState;
	}
	public void setTcpConnState(String tcpConnState) {
		this.tcpConnState = tcpConnState;
	}
	public String getTcpConnLocalAddress() {
		return tcpConnLocalAddress;
	}
	public void setTcpConnLocalAddress(String tcpConnLocalAddress) {
		this.tcpConnLocalAddress = tcpConnLocalAddress;
	}
	public String getTcpConnLocalPort() {
		return tcpConnLocalPort;
	}
	public void setTcpConnLocalPort(String tcpConnLocalPort) {
		this.tcpConnLocalPort = tcpConnLocalPort;
	}
	public String getTcpConnRemAddress() {
		return tcpConnRemAddress;
	}
	public void setTcpConnRemAddress(String tcpConnRemAddress) {
		this.tcpConnRemAddress = tcpConnRemAddress;
	}
	public String getTcpConnRemPort() {
		return tcpConnRemPort;
	}
	public void setTcpConnRemPort(String tcpConnRemPort) {
		this.tcpConnRemPort = tcpConnRemPort;
	}
	@Override
	public String toString() {
		return "TcpProgram [tcpConnState=" + tcpConnState + ", tcpConnLocalAddress=" + tcpConnLocalAddress
				+ ", tcpConnLocalPort=" + tcpConnLocalPort + ", tcpConnRemAddress=" + tcpConnRemAddress
				+ ", tcpConnRemPort=" + tcpConnRemPort + "]";
	}
	
}
