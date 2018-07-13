package com.hzdy.discovery;

public class TreeNode {
	private String ip;
	private String pIp;
	private String name;
	private String macAddress;
	private boolean open;
	private String subnet;
	private int type;


	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getpIp() {
		return pIp;
	}

	public void setpIp(String pIp) {
		this.pIp = pIp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getSubnet() {
		return subnet;
	}

	public void setSubnet(String subnet) {
		this.subnet = subnet;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
public TreeNode(String ip, String pIp, String name, String macAddress, boolean open, String subnet, int type) {
		super();
		this.ip = ip;
		this.pIp = pIp;
		this.name = name;
		this.macAddress = macAddress;
		this.open = open;
		this.subnet = subnet;
		this.type = type;
	}

/*
	public TreeNode(String ip, String pIp, String name, boolean open, String icon) {
		super();
		this.ip = ip;
		this.pIp = pIp;
		this.name = name;
		this.open = open;
		this.icon = icon;
	}
*/
	public TreeNode(String ip, String pIp, String name, boolean open, String subnet, int type) {
		super();
		this.ip = ip;
		this.pIp = pIp;
		this.name = name;
		this.open = open;
		this.subnet = subnet;
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeNode other = (TreeNode) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TreeNode [ip=" + ip + ", pIp=" + pIp + ", name=" + name + ", macAddress=" + macAddress + ", open="
				+ open + ", subnet=" + subnet + ", type=" + type + "]";
	}


}
