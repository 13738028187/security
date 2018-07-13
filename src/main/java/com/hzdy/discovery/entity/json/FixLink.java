package com.hzdy.discovery.entity.json;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class FixLink implements Serializable{
	private FixNode source;
	private FixNode target;
	private float cost;
	private String cost_text;
	
	public FixLink() {
		super();
	}

	public FixNode getSource() {
		return source;
	}

	public void setSource(FixNode source) {
		this.source = source;
	}

	public FixNode getTarget() {
		return target;
	}

	public void setTarget(FixNode target) {
		this.target = target;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
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
		return "FixLink [source=" + source + ", target=" + target + ", cost=" + cost + ", cost_text=" + cost_text + "]";
	}
	
	
	
	
}
