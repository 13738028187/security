package com.hzdy.discovery.entity.json;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;
@Document  
public class Link implements Serializable{
	private String source;
	private String target;
	private int cost;
	private String cost_text;
	
	public Link() {
		super();
	}
	public Link(String source, String target) {
		super();
		this.source = source;
		this.target = target;
		this.cost=1;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getCost_text() {
		return cost_text;
	}
	public void setCost_text(String cost_text) {
		this.cost_text = cost_text;
	}
	@Override
	public String toString() {
		return "Link [source=" + source + ", target=" + target + ", cost=" + cost + ", cost_text=" + cost_text + "]";
	}	
	
}
