package com.hzdy.discovery.entity.json;

public class Router {
	private String destination;
	private String next;
	private String device;
	private int cost;
	private String costText;
	private String source;
	
	
	public Router(String destination, String next, String device, int cost) {
		super();
		this.destination = destination;
		this.next = next;
		this.device = device;
		this.cost = cost;
	}
	public Router(String destination, String next, String device, int cost, String costText, String source) {
		super();
		this.destination = destination;
		this.next = next;
		this.device = device;
		this.cost = cost;
		this.costText = costText;
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getNext() {
		return next;
	}
	public void setNext(String next) {
		this.next = next;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getCostText() {
		return costText;
	}
	public void setCostText(String costText) {
		this.costText = costText;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	@Override
	public String toString() {
		return "Router [destination=" + destination + ", next=" + next + ", device=" + device + ", cost=" + cost
				+ ", costText=" + costText + ", source=" + source + "]";
	}
	
}
