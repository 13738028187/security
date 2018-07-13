package com.hzdy.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SystemRole implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 7783681699251200222L;

    private Integer id;

    private Integer avaliable;//是否可用：0停用，1启用

    private String roleName;

    private String description;

    private Date createDate;

    private List<Integer> priorityIdList;//权限Id

    private List<Priority> priorities;

    private List<SystemUser> systemUsers;

    public List<SystemUser> getSystemUsers() {
        return systemUsers;
    }

    public void setSystemUsers(List<SystemUser> systemUsers) {
        this.systemUsers = systemUsers;
    }

    public List<Priority> getPriorities() {
        return priorities;
    }

    public void setPriorities(List<Priority> priorities) {
        this.priorities = priorities;
    }

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAvaliable() {
        return avaliable;
    }

    public void setAvaliable(Integer avaliable) {
        this.avaliable = avaliable;
    }

    public String getRoleName() {
        return roleName;
    }


    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Date getCreateDate() {
        return createDate;
    }


    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public List<Integer> getPriorityIdList() {
        return priorityIdList;
    }


    public void setPriorityIdList(List<Integer> priorityIdList) {
        this.priorityIdList = priorityIdList;
    }

    @Override
    public String toString() {
        return "SystemRole{" +
                "id=" + id +
                ", avaliable=" + avaliable +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", priorityIdList=" + priorityIdList +
                ", priorities=" + priorities +
                ", systemUsers=" + systemUsers +
                '}';
    }
}