package com.hzdy.hardware.entity;

public class MemoryDetails {
	private String totalJVMMemory;
	private String usedNoHeapMemory;
	private String usedHeapMemory;
	private String bufferedMemory;
	private String garbageCollectionTime;
	private String prcessCpuTime;
	private String freePhysicalMemory;
	private String totalPhysicalMemory;
	private String freeSwapSpace;
	private String totalSwapScpace;

	public String getBufferedMemory() {
		return bufferedMemory;
	}

	public void setBufferedMemory(String bufferedMemory) {
		this.bufferedMemory = bufferedMemory;
	}

	public String getGarbageCollectionTime() {
		return garbageCollectionTime;
	}

	public void setGarbageCollectionTime(String garbageCollectionTime) {
		this.garbageCollectionTime = garbageCollectionTime;
	}

	public String getPrcessCpuTime() {
		return prcessCpuTime;
	}

	public void setPrcessCpuTime(String prcessCpuTime) {
		this.prcessCpuTime = prcessCpuTime;
	}

	public String getFreePhysicalMemory() {
		return freePhysicalMemory;
	}

	public void setFreePhysicalMemory(String freePhysicalMemory) {
		this.freePhysicalMemory = freePhysicalMemory;
	}

	public String getTotalPhysicalMemory() {
		return totalPhysicalMemory;
	}

	public void setTotalPhysicalMemory(String totalPhysicalMemory) {
		this.totalPhysicalMemory = totalPhysicalMemory;
	}

	public String getFreeSwapSpace() {
		return freeSwapSpace;
	}

	public void setFreeSwapSpace(String freeSwapSpace) {
		this.freeSwapSpace = freeSwapSpace;
	}

	public String getTotalSwapScpace() {
		return totalSwapScpace;
	}

	public void setTotalSwapScpace(String totalSwapScpace) {
		this.totalSwapScpace = totalSwapScpace;
	}

	public String getUsedNoHeapMemory() {
		return usedNoHeapMemory;
	}

	public void setUsedNoHeapMemory(String usedNoHeapMemory) {
		this.usedNoHeapMemory = usedNoHeapMemory;
	}

	public String getUsedHeapMemory() {
		return usedHeapMemory;
	}

	public void setUsedHeapMemory(String usedHeapMemory) {
		this.usedHeapMemory = usedHeapMemory;
	}

	public String getTotalJVMMemory() {
		return totalJVMMemory;
	}

	public void setTotalJVMMemory(String totalJVMMemory) {
		this.totalJVMMemory = totalJVMMemory;
	}

}
