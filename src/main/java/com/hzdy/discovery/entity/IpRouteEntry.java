package com.hzdy.discovery.entity;

/**
 * @author Administrator
 *
 * ·��ʵ��
 */
public class IpRouteEntry {
	private String ipRouteDest;
	private int ipRouteIfIndex;
	private String ipRouteNextHop;
	private int ipRouteType;
	private String ipRouteProto;
	private String ipRouteAge;
	private String ipRouteMask;
	private String ipRouteInfo;
	public String getIpRouteDest() {
		return ipRouteDest;
	}
	public void setIpRouteDest(String ipRouteDest) {
		this.ipRouteDest = ipRouteDest;
	}
	public int getIpRouteIfIndex() {
		return ipRouteIfIndex;
	}
	public void setIpRouteIfIndex(int ipRouteIfIndex) {
		this.ipRouteIfIndex = ipRouteIfIndex;
	}
	public String getIpRouteNextHop() {
		return ipRouteNextHop;
	}
	public void setIpRouteNextHop(String ipRouteNextHop) {
		this.ipRouteNextHop = ipRouteNextHop;
	}
	public int getIpRouteType() {
		return ipRouteType;
	}
	public void setIpRouteType(int ipRouteType) {
		this.ipRouteType = ipRouteType;
	}
	public String getIpRouteProto() {
		return ipRouteProto;
	}
	public void setIpRouteProto(String ipRouteProto) {
		this.ipRouteProto = ipRouteProto;
	}
	public String getIpRouteAge() {
		return ipRouteAge;
	}
	public void setIpRouteAge(String ipRouteAge) {
		this.ipRouteAge = ipRouteAge;
	}
	public String getIpRouteMask() {
		return ipRouteMask;
	}
	public void setIpRouteMask(String ipRouteMask) {
		this.ipRouteMask = ipRouteMask;
	}
	public String getIpRouteInfo() {
		return ipRouteInfo;
	}
	public void setIpRouteInfo(String ipRouteInfo) {
		this.ipRouteInfo = ipRouteInfo;
	}
	@Override
	public String toString() {
		return "IpRouteEntry [ipRouteDest=" + ipRouteDest + ", ipRouteIfIndex=" + ipRouteIfIndex + ", ipRouteNextHop="
				+ ipRouteNextHop + ", ipRouteType=" + ipRouteType + ", ipRouteProto=" + ipRouteProto + ", ipRouteAge="
				+ ipRouteAge + ", ipRouteMask=" + ipRouteMask + ", ipRouteInfo=" + ipRouteInfo + "]";
	}
	
}
