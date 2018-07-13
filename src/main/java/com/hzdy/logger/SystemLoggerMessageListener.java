package com.hzdy.logger;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.hzdy.logger.entity.SystemLogger;
import com.hzdy.service.SystemLoggerService;

public class SystemLoggerMessageListener implements MessageListener {
	@Resource
	private SystemLoggerService systemLoggerService;
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
            	System.out.println("收到系统存储信息");
                String text = textMessage.getText();
                System.out.println("text的消息为"+text);
                String[] strList = text.split("-");
                for(int i=0;i<strList.length;i++) {
                	System.out.println(strList[i]);
                }
                System.out.println("解析成功");
    			SystemLogger systemLogger = new SystemLogger();
    			systemLogger.setLogContent(strList[0]);
    			systemLogger.setLogName(strList[3]);
    			systemLogger.setLogType(strList[2]);
    			systemLogger.setLogMessage(strList[4]);
    			System.out.println(systemLogger);
    			systemLoggerService.saveObject(systemLogger);
    			
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}