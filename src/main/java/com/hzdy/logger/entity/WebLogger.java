package com.hzdy.logger.entity;


import java.io.Serializable;

public class WebLogger implements Serializable{
	private int webLoggerId;
	private String ipAddress;
	private String url;
	private String HTTPType;
	private String returnType;
	private String browserType;

	public int getWebLoggerId() {
		return webLoggerId;
	}
	public void setWebLoggerId(int webLoggerId) {
		this.webLoggerId = webLoggerId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHTTPType() {
		return HTTPType;
	}
	public void setHTTPType(String hTTPType) {
		HTTPType = hTTPType;
	}
	
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getBrowserType() {
		return browserType;
	}
	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}
	@Override
	public String toString() {
		return "WebLogger [webLoggerId=" + webLoggerId + ", ipAddress=" + ipAddress + ", url=" + url + ", HTTPType="
				+ HTTPType + ", returnType=" + returnType + ", browserType=" + browserType + "]";
	}
	
}
