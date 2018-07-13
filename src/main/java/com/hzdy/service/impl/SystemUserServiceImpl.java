package com.hzdy.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.hzdy.dao.SystemRoleDao;
import com.hzdy.dao.SystemUserRoleDao;
import com.hzdy.entity.SystemRole;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpRequest;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.hzdy.dao.SystemUserDao;
import com.hzdy.entity.SystemUser;
import com.hzdy.service.SystemUserService;
import com.hzdy.utils.ShiroUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service("systemUserService")
public class SystemUserServiceImpl implements SystemUserService {

    @Resource
    private SystemUserDao systemUserDao;
    @Resource
    private SystemRoleDao systemRoleDao;
    @Resource
    private SystemUserRoleDao systemUserRoleDao;


    @Override
    public List<String> queryAllPerms(Integer userId) {

        return systemUserDao.queryAllPerms(userId);
    }

    @Override
    public List<Integer> queryAllMenuId(Integer userId) {

        return systemUserDao.queryAllMenuId(userId);
    }

    @Override
    public SystemUser queryByLoginName(String username) {

        return systemUserDao.queryByLoginName(username);
    }

    @Override
    public SystemUser queryObject(Integer userId) {

        return systemUserDao.queryObject(userId);
    }

    @Override
    public List<SystemUser> queryList(Map<String, Object> map) {

        return systemUserDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {

        return systemUserDao.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(SystemUser user) throws IllegalArgumentException {

        //先判断 登录名是否存在
        /*if( systemUserDao.queryByLoginName(user.getLoginName())!=null ){
            throw new IllegalArgumentException("登录名已存在！");
		}
		
		RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
		
		String salt = randomNumberGenerator.nextBytes().toHex()+user.getLoginName();*/

        //md5 加密
        /*user.setPassword(ShiroUtils.MD5(user.getPassword(), salt));
        user.setSalt(salt);*///加入干扰字母

        //保存用户
        /*systemUserDao.save(user);*/

        //保存用户与角色关系
        /*systemUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());*/
    }

    @Override
    @Transactional
    public void update(SystemUser user) throws IllegalArgumentException {
		/*if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
			
			String salt = randomNumberGenerator.nextBytes().toHex()+user.getLoginName();
			
			//md5 加密
			user.setPassword(ShiroUtils.MD5(user.getPassword(), salt));
			user.setSalt(salt);//加入干扰字母
		}
		
		systemUserDao.update(user);*/

        //保存用户与角色关系
		/*systemUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());*/
    }

    @Override
    @Transactional
    public void deleteBatch(Integer[] userIds) {
        systemUserDao.deleteBatch(userIds);
    }

    @Override
    public int updatePassword(Integer userId, String password, String newPassword) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("password", password);
        map.put("newPassword", newPassword);

        return systemUserDao.updatePassword(map);
    }

    @Override
    public SystemUser insertSystemUser(HttpServletRequest request) {
        // TODO Auto-generated method stub
        String loginName = request.getParameter("loginName");
        String password = request.getParameter("password");
        String sex = request.getParameter("sex");
        String userName = request.getParameter("userName");

        SystemUser su = new SystemUser();
        su.setLoginName(loginName);
        su.setPassword(password);
        su.setSex(sex);
        su.setUserName(userName);

        SystemUser systemUser = systemUserDao.queryUserByloginNameAnduserName(su.getLoginName());
        if (systemUser != null)
            return null;
        else {
            //传入登录名，MD5加密密码，登录状态。
            RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
            String dd = randomNumberGenerator.nextBytes().toHex() + "13777859596";
            su.setSalt(dd);
            su.setPassword(ShiroUtils.MD5(su.getPassword(), dd));
            su.setAccountStatus(2);
            systemUserDao.insertSystemUser(su);

            List<SystemRole> systemRoles = new ArrayList<>();
            String[] systemRoleId = request.getParameter("systemRoles").split("-");
            for (int i = 0; i < systemRoleId.length; i++) {
            	SystemRole sr = new SystemRole();
                int roleId = Integer.parseInt(systemRoleId[i]);
                systemUserRoleDao.insertSystemUserRole(su.getId(), roleId);
                sr.setId(roleId);
                sr.setRoleName(systemRoleDao.queryNameById(roleId));
                systemRoles.add(sr);
            }
            su.setSystemRoles(systemRoles);

            return su;
        }
    }

    @Override
    public List<SystemUser> queryAllUser() {
        return systemUserDao.queryAllUser();
    }

    @Transactional
    @Override
    public Integer deleteSystemUser(Integer id) {
        try {
            systemUserDao.deleteSystemUser(id);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}