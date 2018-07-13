package com.hzdy.logger;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;

import com.hzdy.logger.entity.Email;
import com.hzdy.logger.entity.EmailContent;
import com.hzdy.logger.utils.MailUtils;
import com.hzdy.service.impl.EmailServiceImpl;


public class EmailQueueMessageListener implements MessageListener {
	@Resource
	private EmailServiceImpl emailService;
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                String text = textMessage.getText();
                System.out.println("接收到消息: " + text);
                System.out.println("发送邮件");
                EmailContent emailContent=new EmailContent();
                emailContent.setContent("测试的内容");
                emailContent.setSubject("捕获到了404网页!");
                emailContent.setSendDate(new Date());
                List<Email> sendEmailList=emailService.queryAll();
                MailUtils.getInstance().sendMail(sendEmailList, emailContent);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }else if(message instanceof MapMessage){
        	try {
				System.out.println(((MapMessage)message).getString("message"));
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }else if(message instanceof ObjectMessage) {
        	try {
				System.out.println(((ObjectMessage)message).getObject());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }else if(message instanceof StreamMessage) {
        	try {
				System.out.println(((StreamMessage)message).readString());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }else if(message instanceof BytesMessage) {
        	byte[] bs=new byte[1024];
        	BytesMessage msg=(BytesMessage)message;
        	try {
				while(msg.readBytes(bs)!=-1) {
					System.out.println(new String(bs));
				}
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
    }

}