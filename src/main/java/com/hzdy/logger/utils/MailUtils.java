package com.hzdy.logger.utils;


import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.hzdy.logger.entity.Email;
import com.hzdy.logger.entity.EmailContent;

public class MailUtils {
	private Session session;
	private String sendMailAccount = "1309014381@qq.com";
	private String senderEmailCode = "jwewrvrnzvjmjiaj";
	private String EmailSMTPHost = "smtp.qq.com";
	private String smtpPort = "465";
	private Properties props ;
	private MailUtils() {
		//创建参数配置, 用于连接邮件服务器的参数配置
		props = new Properties(); // 参数配置
		props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.smtp.host", EmailSMTPHost); // 发件人的邮箱的 SMTP															// 服务器地址
		props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
		// QQ需要开启SSL连接
		props.setProperty("mail.smtp.port", smtpPort);
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.socketFactory.port", smtpPort);
		session = Session.getDefaultInstance(props);
		session.setDebug(true);
	}
	private static MailUtils mailUtils;

	public static MailUtils getInstance() {
		if (mailUtils == null) {
			return mailUtils = new MailUtils();
		} else {
			return mailUtils;
		}
	}
	public boolean sendMail(List<Email> sendEmailList, EmailContent emailContent) {
		MimeMessage message;
		try {
			message = createMimeMessage(session, sendEmailList,emailContent);
			// 4. 根据 Session 获取邮件传输对象
			Transport transport = session.getTransport();
			transport.connect(sendMailAccount, senderEmailCode);
			// 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人
			// 抄送人, 密送人
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public MimeMessage createMimeMessage(Session session, List<Email> sendEmailList, EmailContent emailContent) throws Exception {
		MimeMessage message = new MimeMessage(session);
		//  From: 发件人
		message.setFrom(new InternetAddress(sendMailAccount, "Security系统", "UTF-8"));
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(sendMailAccount,sendMailAccount, "UTF-8"));
		// 添加收信人
		sendEmailList.stream().forEach(a->{
		  try {
				message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(a.getReceiveEmail(),a.getReceiveEmail(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		});
		// Subject: 邮件主题
		message.setSubject(emailContent.getSubject(), "UTF-8");
		// 5. Content: 邮件正文
		message.setContent(emailContent.getContent(), "text/html;charset=UTF-8");
		// 6. 设置发件时间
		Date sendDate=new Date();
		message.setSentDate(sendDate);
		emailContent.setSendDate(sendDate);
		// 7. 保存设置
		message.saveChanges();
		return message;
	}
}
