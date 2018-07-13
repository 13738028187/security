package com.hzdy.discovery.entity.json;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Document;

import com.hzdy.discovery.Host;

@Document  
public class NetworkGraph implements Serializable{
	private String author;
	private String type;
	private String protocol;
	private String area;
	private String revision;
	private String lable;
	private ArrayList<Node> nodes;
	private ArrayList<Link> links;
	private String topology_id;
	private String router_ip;
	private ArrayList<Host> hosts;
	
	public ArrayList<Host> getHosts() {
		return hosts;
	}
	public void setHosts(ArrayList<Host> hosts) {
		this.hosts = hosts;
	}
	public NetworkGraph() {
		super();
		this.nodes=new ArrayList<>();
		this.links=new ArrayList<>();
	}
	public void addNode(Node node) {
		nodes.add(node);
	}
	public void addLink(Link link) {
		links.add(link);
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
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	public ArrayList<Link> getLinks() {
		return links;
	}
	public void setLinks(ArrayList<Link> links) {
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
	
	@Override
	public String toString() {
		return "NetworkGraph [nodes=" + nodes + ", links=" + links + ", hosts=" + hosts + "]";
	}
	
}
