package com.hzdy.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 权限
 * <p>文件名:Priority </p>
 * <p>描述: </p>
 *
 * @author Administrator
 * @version 1.0
 * @date 2017年9月5日 下午2:32:45
 */
public class Priority implements Serializable {

    private static final long serialVersionUID = -4009734077571973745L;

    private Integer priorityId;//权限ID编号

    private Integer parentId;//父级权限ID编号

    private String name;//权限名称

    private String url;//请求路径

    private String perms;//授权

    private Integer nodeType;//节点类型： 0：目录   1：菜单   2：按钮

    private Integer status;//权限状态：0暂停，1启用

    private String icon;//权限图标icon

    private Integer showOrder;//显示顺序

    private String description;

    private Integer priorityType;//限权类型：0-系统后台用户权限，1-微信端用户权限

    private List<SystemRole> systemRoles;

    public List<SystemRole> getSystemRoles() {
        return systemRoles;
    }

    public void setSystemRoles(List<SystemRole> systemRoles) {
        this.systemRoles = systemRoles;
    }

    /**
     * ztree属性
     */
    private Boolean open;

    private String parentName;//父菜单名称

    private List<?> list;

    public Integer getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Integer priorityId) {
        this.priorityId = priorityId;
    }

    public Integer getParentId() {
        return parentId;
    }


    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(Integer priorityType) {
        this.priorityType = priorityType;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Priority [priorityId=" + priorityId + ", parentId=" + parentId + ", name=" + name + ", list=" + list
                + "]";
    }


}