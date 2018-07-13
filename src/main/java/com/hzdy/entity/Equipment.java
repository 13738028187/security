package com.hzdy.entity;

import java.sql.Timestamp;

public class Equipment {
	private Integer id;
	private String equipmentName;
	private String oId;
	private String ip;
	private String workPattern;
	private String onlineTime;
	private Integer onlineStatus; // 0表示掉线，1表示在线
	private Integer isDelete;
	private Timestamp insertDate;
	
	public Timestamp getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public String getoId() {
		return oId;
	}
	public void setoId(String oId) {
		this.oId = oId;
	}
	public String getWorkPattern() {
		return workPattern;
	}
	public void setWorkPattern(String workPattern) {
		this.workPattern = workPattern;
	}
	public String getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}
	public Integer getOnlineStatus() {
		return onlineStatus;
	}
	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Override
	public String toString() {
		return "Equipment [id=" + id + ", equipmentName=" + equipmentName + ", oId=" + oId + ", ip=" + ip
				+ ", workPattern=" + workPattern + ", onlineTime=" + onlineTime + ", onlineStatus=" + onlineStatus
				+ "]";
	}
	
	
}
