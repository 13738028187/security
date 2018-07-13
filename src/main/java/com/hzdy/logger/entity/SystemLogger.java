package com.hzdy.logger.entity;


import java.io.Serializable;

public class SystemLogger implements Serializable {
	private String logName;
	private String logType;
	private int systemLoggerId;
	private String logContent;
	private String logMessage;
	
	public int getSystemLoggerId() {
		return systemLoggerId;
	}
	public void setSystemLoggerId(int systemLoggerId) {
		this.systemLoggerId = systemLoggerId;
	}
	public String getLogName() {
		return logName;
	}
	public void setLogName(String logName) {
		this.logName = logName;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getLogContent() {
		return logContent;
	}
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}
	public String getLogMessage() {
		return logMessage;
	}
	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}
	
}
