package com.hzdy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzdy.dao.PriorityDao;
import com.hzdy.dao.SystemRolePriorityDao;
import com.hzdy.entity.Priority;
import com.hzdy.entity.SystemRole;
import com.hzdy.entity.SystemUser;
import com.hzdy.service.SystemRolePriorityService;
import com.hzdy.service.SystemRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RoleController {

    @Resource
    private SystemRoleService systemRoleService;
    @Resource
    private PriorityDao priorityDao;
    @Resource
    private SystemRolePriorityService systemRolePriorityService;

    //列出所有角色
    @ResponseBody
    @RequestMapping(value = "/listAllRoles")
    public String listAllRoles() {

        List<SystemRole> roles = systemRoleService.queryRoles();

        return JSON.toJSONString(roles);

    }
    
    //分页
    @ResponseBody
    @RequestMapping(value = "/limitListRoles")
    public JSONObject limitListRoles(@RequestParam(value = "page", defaultValue = "1") String page, @RequestParam(value = "rows", defaultValue = "10") String rows) {
    	
    	List<SystemRole> allRole = systemRoleService.queryRoles();
        int totalRecord = allRole.size();
        
        int totalPage = (totalRecord - 1 + Integer.parseInt(rows)) / Integer.parseInt(rows);
        int index = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("total", totalPage);
        jsonObj.put("page", page);
        jsonObj.put("records", totalRecord);
        
        JSONArray row = new JSONArray();
        for (int i = index; i < Integer.parseInt(rows) + index && i < totalRecord; i++) {
        	JSONObject cell = new JSONObject();
        	cell.put("id", allRole.get(i).getId());
        	cell.put("roleName", allRole.get(i).getRoleName());
        	cell.put("avaliable", allRole.get(i).getAvaliable());
        	
        	JSONArray priorities = new JSONArray();
        	for (int j = 0; j < allRole.get(i).getPriorities().size(); j++) {
				priorities.add(allRole.get(i).getPriorities().get(j).getName());
			}
        	cell.put("priorities", priorities);
        	
            row.add(cell);
        }
        jsonObj.put("rows", row);
        
        return jsonObj;
    	
    }

    //列出所有父权限 update by ywm 09.24
    @ResponseBody
    @RequestMapping(value = "/listParentPriority")
    public String listParentPriority() {

        List<Priority> priorities = priorityDao.queryParentPriority();

        return JSON.toJSONString(priorities);

    }
    
    //添加角色
    @ResponseBody
    @RequestMapping(value = "/addRole")
    public Map<String, Object> addRole(@ModelAttribute SystemRole systemRole){
    	
    	boolean flag = systemRoleService.insertRole(systemRole);
    	Map<String, Object> map = new HashMap<>();
    	
    	if(flag == true) {
    		map.put("message", "success");
    		map.put("role", systemRole);
    	} else {
    		map.put("message", "该角色名已存在");
    	}
    	
    	return map;
    	
    }

    //修改角色权限 update by ywm 09.25
    @ResponseBody
    @RequestMapping(value = "/modifyRolePriority", produces = "application/json;charset=utf-8")
    public String modifyRolePriority(HttpServletRequest request) {

        String roleId = request.getParameter("roleid");
        if("".equals(roleId) || roleId == null) {
        	return "无法修改该记录信息";
        }
        String priorities = request.getParameter("priorities");

        return systemRolePriorityService.modifySystemRolePriority(Integer.parseInt(roleId), priorities);
        
    }

    //删除角色
    @ResponseBody
    @RequestMapping(value = "/deleteRole")
    public Map<String, Object> deleteRole(String roleid) {
        Map<String,Object> map = new HashMap<>();
        Integer result = systemRoleService.deleteRole(Integer.parseInt(roleid));
        if(result == 0){
            map.put("message", "failed");
            return map;
        }
        map.put("message", "success");
        return map;
    }

}
