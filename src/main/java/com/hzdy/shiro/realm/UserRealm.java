package com.hzdy.shiro.realm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzdy.entity.SystemUser;
import com.hzdy.service.PriorityService;
import com.hzdy.service.SystemUserService;
import com.hzdy.utils.ShiroUtils;

/**
 * 认证
 * <p>文件名:UserRealm </p>
 * <p>描述: </p>
 * @author Administrator
 * @date 2017年9月4日 下午3:05:59
 * @version 1.0
 *
 */
public class UserRealm extends AuthorizingRealm {
	
    @Autowired
    private SystemUserService systemUserService;
    
    @Autowired
    private PriorityService priorityService;
    
    /**
     * 授权(验证权限时调用)
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		//String adminStr = principals.getPrimaryPrincipal().toString();
		
		Integer userId = ShiroUtils.getUserId();//获取当前登录人的id
		
		List<String> permsList = systemUserService.queryAllPerms(userId);

		//用户权限列表
		Set<String> permsSet = new HashSet<String>();
		for(String perms : permsList){
			if(StringUtils.isBlank(perms)){
				continue;
			}
			permsSet.addAll(Arrays.asList(perms.trim().split(",")));
		}
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet);
		return info;
	}

	/**
	 * 认证(登录时调用)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		
		String username = (String) token.getPrincipal();
		
       //查询用户信息
        SystemUser user = systemUserService.queryByLoginName(username);
        
        //账号不存在
        if(user == null) {
            throw new UnknownAccountException("账号不存在");
        }
        
        //账号锁定
        if(user.getAccountStatus() == 0){
        	throw new LockedAccountException("账号已被暂停使用");
        }
        
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
        		user.getLoginName(), 
        		user.getPassword(), 
        		ByteSource.Util.bytes(user.getSalt()),
        		getName()
        );
        
        return info;
	}
}
