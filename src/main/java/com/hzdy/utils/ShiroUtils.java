package com.hzdy.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import com.hzdy.entity.SystemUser;

/**
 * Shiro工具类
 * <p>文件名:ShiroUtils </p>
 * <p>描述: </p>
 * @author Administrator
 * @date 2017年9月4日 下午2:59:17
 * @version 1.0
 *
 */
public class ShiroUtils {
	
	/**获取shiro session*/
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}
	
	/**获取shiro 身份信息*/
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}
	
	/**获取当前登录人 user*/
	public static SystemUser getUserEntity() {
		return (SystemUser)getSession().getAttribute("user");
	}
	
	/**获取当前登录人的ID*/
	public static Integer getUserId() {
		
		return getUserEntity().getId();
	}
	
	/**设置session */
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}
	
	/**从session里获取 值*/
	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}
	
	/**shiro 退出登录*/
	public static void logout() {
		
		SecurityUtils.getSubject().logout();
	}
	
	/**
	 * shiro 提供加密算法，
	 * @param txt 明文
	 * @param salt 干扰字符串，可以为null
	 * @return
	 */
	public static String MD5(String txt,String salt){
		
        return new SimpleHash(
                "md5",
                txt,
                ByteSource.Util.bytes(salt),252).toHex();
	}
	
	public static void main(String[] args) {
		
		RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
		String dd =randomNumberGenerator.nextBytes().toHex()+"18616909203";
		System.out.println(dd);
		
		System.out.println(MD5("root","cm9vdA=="));
	}
}
