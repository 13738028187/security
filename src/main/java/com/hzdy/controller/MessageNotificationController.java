package com.hzdy.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzdy.entity.SystemUser;
import com.hzdy.logger.entity.Email;
import com.hzdy.redis.dao.IpBlack;
import com.hzdy.redis.service.IpBlackService;
import com.hzdy.service.EmailService;

@Controller
public class MessageNotificationController {
	@Resource
	private IpBlackService ipBlackService;
	@Resource
	private EmailService emailService;


	// 订阅事件
	@ResponseBody
	@RequestMapping(value = "/subscribeLoggerEmail", method = RequestMethod.POST)
	public int subscribeLoggerEmail(HttpSession session) {
		SystemUser sysUser = (SystemUser) session.getAttribute("user");
		List<Email> emails = emailService.queryAllByUserId(sysUser.getId());
		Iterator<Email> it = emails.iterator();
		while (it.hasNext()) {
			Email email = it.next();
			email.setIsSubscribe(1);
			emailService.update(email);
		}
		return 0;
	}

	// 订阅事件
	@ResponseBody
	@RequestMapping(value = "/addEmail", method = RequestMethod.POST)
	public int addEmail(HttpSession session, String receiveEmail) {
		List<Email> emails = new ArrayList<Email>();
		SystemUser sysUser = (SystemUser) session.getAttribute("user");
		emails = emailService.queryAllByUserId(sysUser.getId());
		if (emails.isEmpty()) {
			Email email = new Email();
			email.setReceiveEmail(receiveEmail);
			email.setUserId(sysUser.getId());
			emailService.save(email);
		} else {
			Email email = new Email();
			email.setEmailId(emails.get(0).getEmailId());
			email.setReceiveEmail(receiveEmail);
			email.setUserId(sysUser.getId());
			emailService.update(email);
		}
		return 0;
	}

	// 获取我的邮件
	@ResponseBody
	@RequestMapping(value = "/getMyEmail", method = RequestMethod.GET)
	public List<Email> getMyEmail(HttpSession session) {
		List<Email> emails = new ArrayList<Email>();
		SystemUser sysUser = (SystemUser) session.getAttribute("user");
		emails = emailService.queryAllByUserId(sysUser.getId());
		emails.forEach(System.out::println);
		return emails;
	}
	// ip拦截的数据
	@ResponseBody
	@RequestMapping(value="/getIpBlackList",method=RequestMethod.GET)
	public JSONObject getIpBlackList(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") String page, @RequestParam(value = "rows", defaultValue = "10") String rows) {
		IpBlack ipBlack=ipBlackService.queryAll("ip_blacklist");

		int totalRecord = ipBlack.getVlaue().size();
        
        int totalPage = (totalRecord - 1 + Integer.parseInt(rows)) / Integer.parseInt(rows);
        int index = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("total", totalPage);
        jsonObj.put("page", page);
        jsonObj.put("records", totalRecord);
        
        JSONArray row = new JSONArray();
        List<String> ips=new ArrayList<String>(ipBlack.getVlaue());
        for (int i = index; i < Integer.parseInt(rows) + index && i < totalRecord; i++) {
        	JSONObject cell = new JSONObject();
        	cell.put("ip",ips.get(i));       	        	 	
            row.add(cell);
        }
        jsonObj.put("rows", row);
        
        return jsonObj;		
	}
	// ip拦截的数据
	@ResponseBody
	@RequestMapping(value="/addIpBlack",method=RequestMethod.GET)
	public int addIpBlack(String ip) {
		ipBlackService.save("ip_blacklist", ip);
		return 1;
		
	}
	// ip拦截的数据
	@ResponseBody
	@RequestMapping(value="/deleteIpBlackList",method=RequestMethod.GET)
	public int deleteIpBlackList(String ips) {
		String[] sip=ips.split("-");
		for(int i=0;i<sip.length;i++) {
			ipBlackService.remove("ip_blacklist", sip[i]);
		}
		return 1;
		
	}
}
