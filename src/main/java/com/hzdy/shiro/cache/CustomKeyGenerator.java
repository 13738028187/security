package com.hzdy.shiro.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

/**
 * 自定义 EhCache 的 唯一标示，包名+类名+方法名+参数 作为唯一 key值
 * 
 * @author ZhouGuang
 */
public class CustomKeyGenerator implements KeyGenerator{
	
	/** 
     * 获得cache key的方法，cache key是Cache中一个Element的唯一标识 
     * cache key包括 包名+类名+方法名，如com.tunvan.service.UserServiceImpl.getAllUser 
     */  
	@Override
	public Object generate(Object target, Method method, Object... params) {
	    StringBuffer sb = new StringBuffer();  
	    
	    // class name
	    String className = target.getClass().getName();
	    // method name
	    String methodName = method.getName();
	    
        sb.append(className).append(".").append(methodName);  
        
        if (params != null && params.length > 0) { 
        	//parameter name
        	for(Object param : params){
        		 sb.append(".").append(param);
        	}
        }  
        
		return sb.toString();
	}
}
