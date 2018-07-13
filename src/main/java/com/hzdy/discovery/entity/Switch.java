package com.hzdy.discovery.entity;

import java.util.ArrayList;
import java.util.List;

import com.hzdy.discovery.Host;

public class Switch {
	private Host host;
	private List<String> switchLearnedMac;
	private List<Integer> switchMacPort;

	public Switch(Host host, List<String> switchLearnedMac, List<Integer> switchMacPort) {
		super();
		this.host = host;
		this.switchLearnedMac = switchLearnedMac;
		this.switchMacPort = switchMacPort;
	}
	public Switch(Host host) {
		super();
		this.host = host;
		this.switchLearnedMac = new ArrayList<String>();
		this.switchMacPort = new ArrayList<Integer>();
	}
	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public List<String> getSwitchLearnedMac() {
		return switchLearnedMac;
	}

	public void setSwitchLearnedMac(List<String> switchLearnedMac) {
		this.switchLearnedMac = switchLearnedMac;
	}

	public List<Integer> getSwitchMacPort() {
		return switchMacPort;
	}

	public void setSwitchMacPort(List<Integer> switchMacPort) {
		this.switchMacPort = switchMacPort;
	}

	@Override
	public String toString() {
		return "Switch [host=" + host + ", switchLearnedMac=" + switchLearnedMac + ", switchMacPort=" + switchMacPort
				+ "]";
	}

}
