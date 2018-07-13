package com.hzdy.discovery;

public class Edge {
	private String source;
	private String target;
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
	@Override
	public String toString() {
		return "source:" + source + "--->target:" + target;
	}
	public Edge(String source, String target) {
		super();
		this.source = source;
		this.target = target;
	}
	
	
}
