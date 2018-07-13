package com.hzdy.discovery.entity.json;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Document;

import com.hzdy.discovery.Host;

@Document
public class FixNetworkGraph implements Serializable{
	private String author;
	private String type;
	private String protocol;
	private String area;
	private String revision;
	private String lable;
	private ArrayList<FixNode> nodes;
	private ArrayList<FixLink> links;
	private String topology_id;
	private String router_ip;
	private ArrayList<Host> hosts;
	
	public FixNetworkGraph() {
		super();
		this.nodes=new ArrayList<>();
		this.links=new ArrayList<>();
	}
	public void addNode(FixNode fixNode) {
		nodes.add(fixNode);
	}
	public void addLink(FixLink fixLink) {
		links.add(fixLink);
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public ArrayList<FixNode> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<FixNode> nodes) {
		this.nodes = nodes;
	}
	public ArrayList<FixLink> getLinks() {
		return links;
	}
	public void setLinks(ArrayList<FixLink> links) {
		this.links = links;
	}
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	public String getTopology_id() {
		return topology_id;
	}
	public void setTopology_id(String topology_id) {
		this.topology_id = topology_id;
	}
	public String getRouter_ip() {
		return router_ip;
	}
	public void setRouter_ip(String router_ip) {
		this.router_ip = router_ip;
	}
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	
	public ArrayList<Host> getHosts() {
		return hosts;
	}
	public void setHosts(ArrayList<Host> hosts) {
		this.hosts = hosts;
	}
	@Override
	public String toString() {
		return "FixNetworkGraph [author=" + author + ", type=" + type + ", protocol=" + protocol + ", area=" + area
				+ ", revision=" + revision + ", lable=" + lable + ", nodes=" + nodes + ", links=" + links
				+ ", topology_id=" + topology_id + ", router_ip=" + router_ip + ", hosts=" + hosts + "]";
	}
	
	
	
}
