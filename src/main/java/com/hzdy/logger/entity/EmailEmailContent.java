package com.hzdy.logger.entity;


import java.util.Date;

import javax.mail.internet.MimeMultipart;

public class EmailEmailContent {
	private int emailId;
	private int emailContentId;
	private int isReaded;
	public int getEmailId() {
		return emailId;
	}
	public void setEmailId(int emailId) {
		this.emailId = emailId;
	}
	public int getEmailContentId() {
		return emailContentId;
	}
	public void setEmailContentId(int emailContentId) {
		this.emailContentId = emailContentId;
	}
	public int getIsReaded() {
		return isReaded;
	}
	public void setIsReaded(int isReaded) {
		this.isReaded = isReaded;
	}
	
}
