package com.hzdy.entity;

import java.io.Serializable;
import java.util.List;

public class SystemUser implements Serializable {

    private static final long serialVersionUID = -2884315613871623291L;

    private Integer id;

    private String loginName;

    private transient String password;

    private String sex;

    private String userName;

    private Integer accountStatus;

    private transient String salt;

    /**
     * 角色ID列表
     */
    private List<Integer> roleIdList;

    private List<SystemRole> systemRoles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getSex() {
        return sex;
    }


    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<Integer> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Integer> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }


    public List<SystemRole> getSystemRoles() {
        return systemRoles;
    }

    public void setSystemRoles(List<SystemRole> systemRoles) {
        this.systemRoles = systemRoles;
    }

    @Override
    public String toString() {
        return "SystemUser{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", userName='" + userName + '\'' +
                ", accountStatus=" + accountStatus +
                ", salt='" + salt + '\'' +
                ", systemRoles=" + systemRoles +
                ", roleIdList=" + roleIdList +
                '}';
    }
}