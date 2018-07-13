package com.hzdy.message;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class JavaMailSendUtil {

	public void sendMail(String subject, String from, String[] to, String test, /*String[] filenames,*/ String mineType) {
		try {
			Properties props = new Properties();
			String smtp = "smtp.163.com";// 设置发送邮件所用到的smtp
			String servername = "13456308137@163.com";
			String serverpassword = "ywmzkl14789632";
			Session mailSession = null;
			MimeMessage mimeMsg = null;
			props = java.lang.System.getProperties();
			props.put("mail.smtp.host", smtp);// 设置SMTP主机
			props.put("mail.smtp.auth", "true");// 是否到服务器用户名和密码验证
			// 到服务器验证发送的用户名和密码是否正确
			SmtpAuthenticator myEmailAuther = new SmtpAuthenticator(servername, serverpassword);
			// 设置邮件会话 注意这里将认证信息放进了Session的创建参数里
			mailSession = javax.mail.Session.getInstance(props, (Authenticator) myEmailAuther);
			// 设置传输协议
			javax.mail.Transport transport = mailSession.getTransport("smtp");
			// 设置from、to等信息
			mimeMsg = new javax.mail.internet.MimeMessage(mailSession);
			if (from != null && !from.equals("")) {
				InternetAddress sendFrom = new InternetAddress(from);
				mimeMsg.setFrom(sendFrom);// 设置发送人地址
			}
			InternetAddress[] sendTo = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++) {
				System.out.println("发送到：" + to[i]);
				sendTo[i] = new InternetAddress(to[i]);
			}
			mimeMsg.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO, sendTo);
			mimeMsg.setSubject(subject, "gb2312");
			MimeBodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setContent(test, mineType);

			// 附件传输格式
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			/*for (int i = 0; i < filenames.length; i++) {
				MimeBodyPart messageBodyPart2 = new MimeBodyPart();
				String filename = filenames[i].split(";")[0];
				System.out.println(filename);
				String displatname = filenames[i].split(";")[1];
				// 得到数据源
				FileDataSource fds = new FileDataSource(filename);
				// BodyPart添加附件本身
				messageBodyPart2.setDataHandler(new DataHandler(fds));
				// BodyPart添加附件文件名
				messageBodyPart2.setFileName(MimeUtility.encodeText(displatname));
				multipart.addBodyPart(messageBodyPart2);
			}*/
			mimeMsg.setContent(multipart);
			// 设置信件头的发送日期
			mimeMsg.setSentDate(new Date());
			mimeMsg.saveChanges();
			// 发送邮件
			Transport.send(mimeMsg);
			transport.close();
			System.out.println("消息发送成功");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
