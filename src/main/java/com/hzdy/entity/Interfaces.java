package com.hzdy.entity;

public class Interfaces {
	private int id;
	private String interfaceName;
	private int securityAreaId;
	private String ifType;
	private String ifMtu;
	private String ifLastChange;
	private String ifInOctets;
	private String ifInUcastPkts;
	private String ifInNUcastPkts;
	private String ifInDiscards;
	private String ifInErrors;
	private String ifInUnknownProtos;
	private String ifOutOctets;
	private String ifOutUcastPkts;
	private String ifOutNUcastPkts;
	private String ifOutDiscards;	
	private String ifOutErrors;
	private String ifOutQLen;
	private String ifSpecific;
	private String ifPhysAddress;
	public Interfaces() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	
	public int getSecurityAreaId() {
		return securityAreaId;
	}
	public void setSecurityAreaId(int securityAreaId) {
		this.securityAreaId = securityAreaId;
	}
	
	
	public String getIfType() {
		return ifType;
	}
	public void setIfType(String ifType) {
		this.ifType = ifType;
	}
	public String getIfMtu() {
		return ifMtu;
	}
	public void setIfMtu(String ifMtu) {
		this.ifMtu = ifMtu;
	}
	public String getIfLastChange() {
		return ifLastChange;
	}
	public void setIfLastChange(String ifLastChange) {
		this.ifLastChange = ifLastChange;
	}
	public String getIfInOctets() {
		return ifInOctets;
	}
	public void setIfInOctets(String ifInOctets) {
		this.ifInOctets = ifInOctets;
	}
	public String getIfInUcastPkts() {
		return ifInUcastPkts;
	}
	public void setIfInUcastPkts(String ifInUcastPkts) {
		this.ifInUcastPkts = ifInUcastPkts;
	}
	public String getIfInNUcastPkts() {
		return ifInNUcastPkts;
	}
	public void setIfInNUcastPkts(String ifInNUcastPkts) {
		this.ifInNUcastPkts = ifInNUcastPkts;
	}
	public String getIfInDiscards() {
		return ifInDiscards;
	}
	public void setIfInDiscards(String ifInDiscards) {
		this.ifInDiscards = ifInDiscards;
	}
	public String getIfInErrors() {
		return ifInErrors;
	}
	public void setIfInErrors(String ifInErrors) {
		this.ifInErrors = ifInErrors;
	}
	public String getIfInUnknownProtos() {
		return ifInUnknownProtos;
	}
	public void setIfInUnknownProtos(String ifInUnknownProtos) {
		this.ifInUnknownProtos = ifInUnknownProtos;
	}
	public String getIfOutOctets() {
		return ifOutOctets;
	}
	public void setIfOutOctets(String ifOutOctets) {
		this.ifOutOctets = ifOutOctets;
	}
	public String getIfOutUcastPkts() {
		return ifOutUcastPkts;
	}
	public void setIfOutUcastPkts(String ifOutUcastPkts) {
		this.ifOutUcastPkts = ifOutUcastPkts;
	}
	public String getIfOutNUcastPkts() {
		return ifOutNUcastPkts;
	}
	public void setIfOutNUcastPkts(String ifOutNUcastPkts) {
		this.ifOutNUcastPkts = ifOutNUcastPkts;
	}
	public String getIfOutDiscards() {
		return ifOutDiscards;
	}
	public void setIfOutDiscards(String ifOutDiscards) {
		this.ifOutDiscards = ifOutDiscards;
	}
	public String getIfOutErrors() {
		return ifOutErrors;
	}
	public void setIfOutErrors(String ifOutErrors) {
		this.ifOutErrors = ifOutErrors;
	}
	public String getIfOutQLen() {
		return ifOutQLen;
	}
	public void setIfOutQLen(String ifOutQLen) {
		this.ifOutQLen = ifOutQLen;
	}
	public String getIfSpecific() {
		return ifSpecific;
	}
	public void setIfSpecific(String ifSpecific) {
		this.ifSpecific = ifSpecific;
	}
	public String getIfPhysAddress() {
		return ifPhysAddress;
	}
	public void setIfPhysAddress(String ifPhysAddress) {
		this.ifPhysAddress = ifPhysAddress;
	}
	@Override
	public String toString() {
		return "Interfaces [id=" + id + ", interfaceName=" + interfaceName + ", securityAreaId=" + securityAreaId
				+ ", ifType=" + ifType + ", ifMtu=" + ifMtu + ", ifLastChange=" + ifLastChange + ", ifInOctets="
				+ ifInOctets + ", ifInUcastPkts=" + ifInUcastPkts + ", ifInNUcastPkts=" + ifInNUcastPkts
				+ ", ifInDiscards=" + ifInDiscards + ", ifInErrors=" + ifInErrors + ", ifInUnknownProtos="
				+ ifInUnknownProtos + ", ifOutOctets=" + ifOutOctets + ", ifOutUcastPkts=" + ifOutUcastPkts
				+ ", ifOutNUcastPkts=" + ifOutNUcastPkts + ", ifOutDiscards=" + ifOutDiscards + ", ifOutErrors="
				+ ifOutErrors + ", ifOutQLen=" + ifOutQLen + ", ifSpecific=" + ifSpecific + ", ifPhysAddress="
				+ ifPhysAddress + "]";
	}
	
}
