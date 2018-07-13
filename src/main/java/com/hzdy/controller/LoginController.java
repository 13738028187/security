package com.hzdy.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzdy.dao.MySessionDAO;
import com.hzdy.entity.SystemUser;
import com.hzdy.service.SystemUserService;
import com.hzdy.utils.R;
import com.hzdy.utils.ShiroUtils;
import com.hzdy.websocket.MySessionListener;
import com.hzdy.websocket.MySessionSingleton;



/**
 * 后台系统 登录
 * 
 * <p>文件名:LoginController </p>
 * <p>描述: </p>
 * @author Administrator
 * @date 2017年8月30日 下午4:00:18
 * @version 1.0
 */
@Controller
public class LoginController {
	
	@Resource
	private SystemUserService systemUserService;
	
	@Resource
	private MySessionDAO mySessionDAO;
	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	public R login(String username, String password,HttpSession session)throws IOException {
		try{
			Subject subject = ShiroUtils.getSubject();

			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			
			subject.login(token);
			
		}catch (UnknownAccountException e) {
			return R.error(e.getMessage());
		}catch (IncorrectCredentialsException e) {
			return R.error(e.getMessage());
		}catch (LockedAccountException e) {
			return R.error(e.getMessage());
		}catch (ExcessiveAttemptsException e) {
			return R.error(e.getMessage());
		}catch (AuthenticationException e) {
			return R.error("账户验证失败");
		}
	    
		return R.ok();
	}
	
	/**
	 * 退出
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		ShiroUtils.logout();
		return "redirect:login.html";
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	@ResponseBody
	public R info(HttpSession session){
		SystemUser sysUser= (SystemUser) session.getAttribute("user");
		return R.ok().put("user",sysUser);
	}
	/**
	 * 进行 路由 设置
	 * @param url
	 * @return
	 */
	@RequestMapping("{suffix}/{url}.html")
	public String router(@PathVariable("suffix") String suffix,@PathVariable("url") String url){
		return suffix+"/" + url + ".html";
	}
	
	@RequestMapping("active/sessions")
	@ResponseBody
	public long getActiveSessions(){
		return MySessionSingleton.getInstance().getActiveUser();
	}
}
