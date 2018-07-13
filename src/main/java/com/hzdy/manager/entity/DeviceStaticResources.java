package com.hzdy.manager.entity;


import java.util.List;

/**
 * 
 * what
 * 
 * @author
 * @version 0.1
 */
public class DeviceStaticResources {
	
	private int id;
	/**
	 * 获取系统基本信息 sysDesc
	 */
	private String sysDesc;

	/**
	 * 监控时间 sysUptime
	 */
	private String sysUptime;

	/**
	 * 机器名 sysName
	 */
	private String sysName;

	/**
	 * 系统联系人 sysContact
	 */
	private String sysContact;

	/**
	 * 机器提供的服务 sysService
	 */
	private String sysService;

	/**
	 * 机器坐在位置 sysLocation
	 */
	private String sysLocation;

	/**
	 * 获取内存大小 hrMemorySize
	 */
	private String hrMemorySize;

	/**
	 * 网络接口的数目 ifNumber
	 */
	private String ifNumber;

	/**
	 * 接口的物理地址 ifPhysAddress
	 */
	private List<String> ifPhysAddress;

	/**
	 * 网络接口信息描述 ifDescr
	 */
	private List<String> ifDescr;

	/**
	 * 网络接口类型 ifType
	 */
	private List<String> ifType;

	/**
	 * 系统安装的软件列表 hrSWInstalledName
	 */
	private List<String> hrSWInstalledName;

	public String getSysDesc() {
		return sysDesc;
	}

	public void setSysDesc(String sysDesc) {
		this.sysDesc = sysDesc;
	}

	public String getSysUptime() {
		return sysUptime;
	}

	public void setSysUptime(String sysUptime) {
		this.sysUptime = sysUptime;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSysContact() {
		return sysContact;
	}

	public void setSysContact(String sysContact) {
		this.sysContact = sysContact;
	}

	public String getSysService() {
		return sysService;
	}

	public void setSysService(String sysService) {
		this.sysService = sysService;
	}

	public String getSysLocation() {
		return sysLocation;
	}

	public void setSysLocation(String sysLocation) {
		this.sysLocation = sysLocation;
	}

	public String getHrMemorySize() {
		return hrMemorySize;
	}

	public void setHrMemorySize(String hrMemorySize) {
		this.hrMemorySize = hrMemorySize;
	}

	public String getIfNumber() {
		return ifNumber;
	}

	public void setIfNumber(String ifNumber) {
		this.ifNumber = ifNumber;
	}

	public List<String> getIfPhysAddress() {
		return ifPhysAddress;
	}

	public void setIfPhysAddress(List<String> ifPhysAddress) {
		this.ifPhysAddress = ifPhysAddress;
	}

	public List<String> getIfDescr() {
		return ifDescr;
	}

	public void setIfDescr(List<String> ifDescr) {
		this.ifDescr = ifDescr;
	}

	public List<String> getIfType() {
		return ifType;
	}

	public void setIfType(List<String> ifType) {
		this.ifType = ifType;
	}

	public List<String> getHrSWInstalledName() {
		return hrSWInstalledName;
	}

	public void setHrSWInstalledName(List<String> hrSWInstalledName) {
		this.hrSWInstalledName = hrSWInstalledName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "DeviceStaticResources [id=" + id + ", sysDesc=" + sysDesc + ", sysUptime=" + sysUptime + ", sysName="
				+ sysName + ", sysContact=" + sysContact + ", sysService=" + sysService + ", sysLocation=" + sysLocation
				+ ", hrMemorySize=" + hrMemorySize + ", ifNumber=" + ifNumber + ", ifPhysAddress=" + ifPhysAddress
				+ ", ifDescr=" + ifDescr + ", ifType=" + ifType + ", hrSWInstalledName=" + hrSWInstalledName + "]";
	}
	

}
