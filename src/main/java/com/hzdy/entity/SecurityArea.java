package com.hzdy.entity;

import java.util.List;

public class SecurityArea {
	private int id;
	private String name;
	private int level;
	private int size;//接口数量
	private String include_interface;
	private List<Interfaces> interlist;
	public SecurityArea() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getInclude_interface() {
		return include_interface;
	}
	public void setInclude_interface(String include_interface) {
		this.include_interface = include_interface;
	}
	public List<Interfaces> getInterlist() {
		return interlist;
	}
	public void setInterlist(List<Interfaces> interlist) {
		this.interlist = interlist;
	}
	@Override
	public String toString() {
		return "SecurityArea [id=" + id + ", name=" + name + ", level=" + level + ", size=" + size
				+ ", include_interface=" + include_interface + ", interlist=" + interlist + "]";
	}
	
	
	
	
}
