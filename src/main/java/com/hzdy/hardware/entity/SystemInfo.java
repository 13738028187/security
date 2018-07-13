package com.hzdy.hardware.entity;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.Locale;

import com.hzdy.hardware.Parameters;

/**
 * @author zyd
 *
 */
public  class SystemInfo implements Serializable {
	private int id;
	private static final long serialVersionUID = 1L;
	// 获取操作系统开机时间
	private Date startDate;
	// 获取本机可用的CPU数量
	private int availableProcessors;
	// 获取操作系统的类型
	private String os;
	// 获取主机的信息
	private String host;
	// 获取Java的版本
	private String javaVersion;
	// 获取虚拟机版本
	private String jvmVersion;
	// 获取当前进程PID
	private String pid;
	// 虚拟机运行参数
	private String jvmArguments;
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public int getAvailableProcessors() {
		return availableProcessors;
	}


	public void setAvailableProcessors(int availableProcessors) {
		this.availableProcessors = availableProcessors;
	}


	public String getOs() {
		return os;
	}


	public void setOs(String os) {
		this.os = os;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public String getJavaVersion() {
		return javaVersion;
	}


	public void setJavaVersion(String javaVersion) {
		this.javaVersion = javaVersion;
	}


	public String getJvmVersion() {
		return jvmVersion;
	}


	public void setJvmVersion(String jvmVersion) {
		this.jvmVersion = jvmVersion;
	}


	public String getPid() {
		return pid;
	}


	public void setPid(String pid) {
		this.pid = pid;
	}


	public String getJvmArguments() {
		return jvmArguments;
	}


	public void setJvmArguments(String jvmArguments) {
		this.jvmArguments = jvmArguments;
	}


	@Override
	public String toString() {
		return "SystemInfo [id=" + id + ", startDate=" + startDate + ", availableProcessors=" + availableProcessors
				+ ", os=" + os + ", host=" + host + ", javaVersion=" + javaVersion + ", jvmVersion=" + jvmVersion
				+ ", pid=" + pid + ", jvmArguments=" + jvmArguments + "]";
	}

}
