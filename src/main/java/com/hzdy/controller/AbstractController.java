package com.hzdy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hzdy.entity.SystemUser;
import com.hzdy.utils.ShiroUtils;

/**
 * Controller公共组件
 * <p>文件名:AbstractController </p>
 * <p>描述: </p>
 * @author Administrator
 * @date 2017年9月4日 下午3:06:21
 * @version 1.0
 *
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SystemUser getUser() {
		return ShiroUtils.getUserEntity();
	}
	
	protected Integer getUserId() {
		return ShiroUtils.getUserId();
	}
}
