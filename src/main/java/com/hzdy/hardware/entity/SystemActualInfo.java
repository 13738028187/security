package com.hzdy.hardware.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.alibaba.fastjson.annotation.JSONField;
@Document  
public class SystemActualInfo implements Serializable{
	private String startDate;
	private int maxPhysicalMemorySize;
	private int maxSwapSpaceSize;
	private int maxMemory;
	private int usedPhysicalMemorySize;
	private int usedSwapSpaceSize;
	private int usedMemory;
	// cpu负载情况
	private double systemCpuLoad;
	

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public int getMaxPhysicalMemorySize() {
		return maxPhysicalMemorySize;
	}

	public void setMaxPhysicalMemorySize(int maxPhysicalMemorySize) {
		this.maxPhysicalMemorySize = maxPhysicalMemorySize;
	}

	public int getMaxSwapSpaceSize() {
		return maxSwapSpaceSize;
	}

	public void setMaxSwapSpaceSize(int maxSwapSpaceSize) {
		this.maxSwapSpaceSize = maxSwapSpaceSize;
	}

	public int getMaxMemory() {
		return maxMemory;
	}

	public void setMaxMemory(int maxMemory) {
		this.maxMemory = maxMemory;
	}

	public int getUsedPhysicalMemorySize() {
		return usedPhysicalMemorySize;
	}

	public void setUsedPhysicalMemorySize(int usedPhysicalMemorySize) {
		this.usedPhysicalMemorySize = usedPhysicalMemorySize;
	}

	public int getUsedSwapSpaceSize() {
		return usedSwapSpaceSize;
	}

	public void setUsedSwapSpaceSize(int usedSwapSpaceSize) {
		this.usedSwapSpaceSize = usedSwapSpaceSize;
	}

	public int getUsedMemory() {
		return usedMemory;
	}

	public void setUsedMemory(int usedMemory) {
		this.usedMemory = usedMemory;
	}

	public double getSystemCpuLoad() {
		return systemCpuLoad;
	}

	public void setSystemCpuLoad(double systemCpuLoad) {
		this.systemCpuLoad = systemCpuLoad;
	}

	@Override
	public String toString() {
		return "SystemActualInfo [startDate=" + startDate + ", maxPhysicalMemorySize=" + maxPhysicalMemorySize
				+ ", maxSwapSpaceSize=" + maxSwapSpaceSize + ", maxMemory=" + maxMemory + ", usedPhysicalMemorySize="
				+ usedPhysicalMemorySize + ", usedSwapSpaceSize=" + usedSwapSpaceSize + ", usedMemory=" + usedMemory
				+ ", systemCpuLoad=" + systemCpuLoad + "]";
	}
	
}
