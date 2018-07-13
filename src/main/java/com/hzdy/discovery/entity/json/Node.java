package com.hzdy.discovery.entity.json;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Document;
@Document  
public class Node implements Serializable{
	private String ip;
	private String label;
	private ArrayList<String> localAddresses;
	private String type;
	
/*	private Property properties;*/
	
	public Node() {
		super();
	}
	public Node(String ip, String label) {
		super();
		this.ip = ip;
		this.label = label;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public ArrayList<String> getLocalAddresses() {
		return localAddresses;
	}
	public void setLocalAddresses(ArrayList<String> localAddresses) {
		this.localAddresses = localAddresses;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Node [ip=" + ip + ", label=" + label + ", localAddresses=" + localAddresses + ", type=" + type + "]";
	}

}
