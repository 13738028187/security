package com.hzdy.entity;

import java.io.Serializable;

public class SystemRolePriority implements Serializable{
   
	private static final long serialVersionUID = 3584040274167525327L;

	private Integer roleId;

    private Integer priorityId;

    public Integer getRoleId() {
        return roleId;
    }

    
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
    
    public Integer getPriorityId() {
        return priorityId;
    }

    
    public void setPriorityId(Integer priorityId) {
        this.priorityId = priorityId;
    }
}