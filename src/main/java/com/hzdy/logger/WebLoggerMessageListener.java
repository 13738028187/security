package com.hzdy.logger;

import java.util.List;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.hzdy.logger.entity.WebLogger;
import com.hzdy.service.impl.WebLoggerServiceImpl;

public class WebLoggerMessageListener implements MessageListener {
	@Resource
	private WebLoggerServiceImpl webLoggerService;
	 public void onMessage(Message message) {
	        if (message instanceof TextMessage) {
	            TextMessage textMessage = (TextMessage) message;
	            try {
	            	System.out.println("收到Web存储信息:开始存储...");
	                String text = textMessage.getText();
	               /* System.out.println(text);*/
	    			String[] strList = text.split("--");
	/*    	        for(int i=0;i<strList.length;i++) {
	    	        	System.out.println(strList[i]);
	    	        }*/
	    			WebLogger webLogger = new WebLogger();
	    			
	    			webLogger.setIpAddress(strList[0]+strList[1]);
	    		
	    			webLogger.setUrl(strList[2]);
	    		
	    			webLogger.setBrowserType(strList[6]);
	    	
	    			webLogger.setHTTPType(strList[4]);
	    			webLogger.setReturnType(strList[3]);
	    			webLogger.setWebLoggerId(2);
	    			List<WebLogger> list=webLoggerService.queryAll();
	    			System.out.println("成功查询");
	    			System.out.println(webLogger.toString());
	    			webLoggerService.saveObject(webLogger);
	    			System.out.println("保存成功");
	            } catch (JMSException e) {
	                e.printStackTrace();
	            }
	        }
	    }
}