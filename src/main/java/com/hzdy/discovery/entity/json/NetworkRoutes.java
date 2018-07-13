package com.hzdy.discovery.entity.json;

import java.util.ArrayList;

public class NetworkRoutes{
	private String type;
	private String protocol;
	private String  version;
	private String metric;
    private ArrayList<Router> routers;
    private String revision;
    private String topologyId;
    private String routeId;
    
	public NetworkRoutes(String type, String protocol, String version, String metric) {
		super();
		this.type = type;
		this.protocol = protocol;
		this.version = version;
		this.metric = metric;
		this.routers=new ArrayList<Router>();
	}
	public void addRoute(String destination,String next,String device,int cost) {
		routers.add(new Router(destination,next,device,cost));
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMetric() {
		return metric;
	}
	public void setMetric(String metric) {
		this.metric = metric;
	}
	public ArrayList<Router> getRouters() {
		return routers;
	}
	public void setRouters(ArrayList<Router> routers) {
		this.routers = routers;
	}
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	public String getTopologyId() {
		return topologyId;
	}
	public void setTopologyId(String topologyId) {
		this.topologyId = topologyId;
	}
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	@Override
	public String toString() {
		return "NetworkRoutes [type=" + type + ", protocol=" + protocol + ", version=" + version + ", metric=" + metric
				+ ", routers=" + routers + ", revision=" + revision + ", topologyId=" + topologyId + ", routeId="
				+ routeId + "]";
	}
    
}
