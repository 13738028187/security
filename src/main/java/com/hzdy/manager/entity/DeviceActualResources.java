package com.hzdy.manager.entity;



import java.util.List;

/**
* 
* what
* 
* @author 
* @version 0.1
*/
public class DeviceActualResources {
	private String ip;
	
	private String sysName;
	/**
	 * 系统运行的进程列表
	 * hrSWRunName
	 */
	private List<String> hrSWRunName;
	
	/**
	 * Total RAM used 总内存被使用了多少
	 * hrMemorySize
	 */
	private double memAvailReal;
	
	/**
	 * CPU的当前负载，N个核就有N个负载
	 * hrProcessorLoadAvg
	 */
	private int hrProcessorLoadAvg;
	
	
	/**
	 * 接口发送和接收的最大IP数据报[BYTE]
	 * ifMTU
	 */
	private List<String> ifMTU;
	
	/**
	 * 接口当前带宽[bps]
	 * ifSpeed
	 */
	private List<String> ifSpeed;
	
	/**
	 * 接口当前操作状态[up|down]
	 * ifOperStatus
	 */
	private List<String> ifOperStatus;
	
	/**
	 * 接口收到的字节数
	 * ifInOctet
	 */
	private List<String> ifInOctet;
	
	/**
	 * 接口发送的字节数
	 * ifOutOctet
	 */
	private List<String> ifOutOctet;
	
	/**
	 * 接口收到的数据包个数
	 * ifInUcastPkts
	 */
	private List<String> ifInUcastPkts;
	
	/**
	 * 	接口发送的数据包个数
	 * ifOutUcastPkts
	 */
	private List<String> ifOutUcastPkts;
	
	

	public List<String> getHrSWRunName() {
		return hrSWRunName;
	}

	public void setHrSWRunName(List<String> hrSWRunName) {
		this.hrSWRunName = hrSWRunName;
	}

	public double getMemAvailReal() {
		return memAvailReal;
	}

	public void setMemAvailReal(double d) {
		this.memAvailReal = d;
	}

	public int getHrProcessorLoadAvg() {
		return hrProcessorLoadAvg;
	}

	public void setHrProcessorLoadAvg(int i) {
		this.hrProcessorLoadAvg = i;
	}

	public List<String> getIfMTU() {
		return ifMTU;
	}

	public void setIfMTU(List<String> ifMTU) {
		this.ifMTU = ifMTU;
	}

	public List<String> getIfSpeed() {
		return ifSpeed;
	}

	public void setIfSpeed(List<String> ifSpeed) {
		this.ifSpeed = ifSpeed;
	}

	public List<String> getIfOperStatus() {
		return ifOperStatus;
	}

	public void setIfOperStatus(List<String> ifOperStatus) {
		this.ifOperStatus = ifOperStatus;
	}

	public List<String> getIfInOctet() {
		return ifInOctet;
	}

	public void setIfInOctet(List<String> ifInOctet) {
		this.ifInOctet = ifInOctet;
	}

	public List<String> getIfOutOctet() {
		return ifOutOctet;
	}

	public void setIfOutOctet(List<String> ifOutOctet) {
		this.ifOutOctet = ifOutOctet;
	}

	public List<String> getIfInUcastPkts() {
		return ifInUcastPkts;
	}

	public void setIfInUcastPkts(List<String> ifInUcastPkts) {
		this.ifInUcastPkts = ifInUcastPkts;
	}

	public List<String> getIfOutUcastPkts() {
		return ifOutUcastPkts;
	}

	public void setIfOutUcastPkts(List<String> ifOutUcastPkts) {
		this.ifOutUcastPkts = ifOutUcastPkts;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	@Override
	public String toString() {
		return "DeviceActualResources [ip=" + ip + ", sysName=" + sysName + ", hrSWRunName=" + hrSWRunName
				+ ", memAvailReal=" + memAvailReal + ", hrProcessorLoadAvg=" + hrProcessorLoadAvg + ", ifMTU=" + ifMTU
				+ ", ifSpeed=" + ifSpeed + ", ifOperStatus=" + ifOperStatus + ", ifInOctet=" + ifInOctet
				+ ", ifOutOctet=" + ifOutOctet + ", ifInUcastPkts=" + ifInUcastPkts + ", ifOutUcastPkts="
				+ ifOutUcastPkts + "]";
	}


}
