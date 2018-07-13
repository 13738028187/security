package com.hzdy.discovery.entity.json;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class FixNode implements Serializable{
	private String ip;
	private String label;
	private String type;
	private String localAddresses;
	private String linkCount;
	private int index;
	private int weight;
	private double x;
	private double y;
	private double px;
	private double py;
	
	public FixNode() {
		super();
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
	public String getLocalAddresses() {
		return localAddresses;
	}
	public void setLocalAddresses(String localAddresses) {
		this.localAddresses = localAddresses;
	}
	public String getLinkCount() {
		return linkCount;
	}
	public void setLinkCount(String linkCount) {
		this.linkCount = linkCount;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getPx() {
		return px;
	}
	public void setPx(double px) {
		this.px = px;
	}
	public double getPy() {
		return py;
	}
	public void setPy(double py) {
		this.py = py;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "FixNode [ip=" + ip + ", label=" + label + ", type=" + type + ", localAddresses=" + localAddresses
				+ ", linkCount=" + linkCount + ", index=" + index + ", weight=" + weight + ", x=" + x + ", y=" + y
				+ ", px=" + px + ", py=" + py + "]";
	}
	
	
}
