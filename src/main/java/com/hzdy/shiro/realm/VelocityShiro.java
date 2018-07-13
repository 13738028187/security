package com.hzdy.shiro.realm;

import java.util.List;

import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hzdy.utils.ShiroUtils;

/**
 * Shiro权限标签(Velocity版)
 * 
 * <p>文件名:VelocityShiro </p>
 * <p>描述: </p>
 * @author Administrator
 * @date 2017年9月4日 下午2:38:35
 * @version 1.0
 */
public class VelocityShiro {

	private final static Logger logger = LoggerFactory.getLogger(VelocityShiro.class);
	
	/**
	 * 后台判断 当前登录人 是否拥有该权限
	 * @param permission  权限标识
	 * @return   true：是     false：否
	 */
	public boolean hasPermission(String permission) {
		Subject subject = ShiroUtils.getSubject();
		return subject != null && subject.isPermitted(permission);
	}
	
	
	
}
