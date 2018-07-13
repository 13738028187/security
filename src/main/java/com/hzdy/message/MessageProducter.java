package com.hzdy.message;

import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageProducter {

	// ActiveMq 的默认用户名
	private static final String USERNAME = "admin";
	// ActiveMq 的默认登录密码
	private static final String PASSWORD = "admin";
	// ActiveMQ 的链接地址
	private static final String BROKEN_URL = "tcp://192.168.2.9:61616";

	AtomicInteger count = new AtomicInteger(0);
	// 链接工厂
	ConnectionFactory connectionFactory;
	// 链接对象
	Connection connection;
	// 事务管理
	Session session;
	ThreadLocal<MessageProducer> threadLocal = new ThreadLocal<>();

	public void init() {
		try {
			// 创建一个链接工厂
			connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEN_URL);
			// 从工厂中创建一个链接
			connection = connectionFactory.createConnection();
			// 开启链接
			connection.start();
			// 创建一个事务（这里通过参数可以设置事务的级别）
			session = connection.createSession(true, Session.SESSION_TRANSACTED);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String message) {
		try {
			// 创建一个消息队列
			Topic topic = session.createTopic("snmp");
			// 消息生产者
			MessageProducer messageProducer = null;
			if (threadLocal.get() != null) {
				messageProducer = threadLocal.get();
			} else {
				messageProducer = session.createProducer(topic);
				threadLocal.set(messageProducer);
			}
			// 创建一条消息
			TextMessage msg = session.createTextMessage(message);
			// 发送消息
			messageProducer.send(msg);
			// 提交事务
			session.commit();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
