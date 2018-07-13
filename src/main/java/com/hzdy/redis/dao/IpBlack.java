package com.hzdy.redis.dao;

import java.util.Set;

public class IpBlack {
	private String key;
	private Set<String> vlaue;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Set<String> getVlaue() {
		return vlaue;
	}
	public void setVlaue(Set<String> vlaue) {
		this.vlaue = vlaue;
	}
	
}
