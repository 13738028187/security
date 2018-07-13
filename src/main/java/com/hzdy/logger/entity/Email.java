package com.hzdy.logger.entity;


import java.io.Serializable;

public class Email implements Serializable {

	private static final long serialVersionUID = -4160417306762200959L;
	private Integer emailId;
	private String receiveEmail;
	private Integer isDelete;
	private Integer userId;
	private Integer isSubscribe;
	
	public Integer getEmailId() {
		return emailId;
	}
	public void setEmailId(Integer emailId) {
		this.emailId = emailId;
	}
	public String getReceiveEmail() {
		return receiveEmail;
	}
	public void setReceiveEmail(String receiveEmail) {
		this.receiveEmail = receiveEmail;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getIsSubscribe() {
		return isSubscribe;
	}
	public void setIsSubscribe(Integer isSubscribe) {
		this.isSubscribe = isSubscribe;
	}
	
}
