package com.hzdy.logger.entity;


import java.util.Date;

import javax.mail.internet.MimeMultipart;

public class EmailContent {
	private String subject;
	private MimeMultipart body;
	private String content;
	private Date sendDate;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public MimeMultipart getBody() {
		return body;
	}
	public void setBody(MimeMultipart body) {
		this.body = body;
	}

	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
