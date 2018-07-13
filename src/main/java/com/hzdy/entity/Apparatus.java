package com.hzdy.entity;
/**
* 
* what
* 
* @author kirohuji
* @version 0.1
*/
public class Apparatus {
	private Integer apparatusId;
	
	private String number;
	
	private String name;
	
	private String type;
	
	private String money;
	
	private String principal;
	
	private String comment;

	public Integer getApparatusId() {
		return apparatusId;
	}

	public void setApparatusId(Integer apparatusId) {
		this.apparatusId = apparatusId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Apparatus [apparatusId=" + apparatusId + ", number=" + number + ", name=" + name + ", type=" + type
				+ ", money=" + money + ", principal=" + principal + ", comment=" + comment + "]";
	}
	
	
	
}
