package com.hzdy.websocket;
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
import org.springframework.messaging.Message;  
import org.springframework.messaging.MessageChannel;  
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;  
import org.springframework.messaging.support.ChannelInterceptorAdapter;  
import org.springframework.stereotype.Component;  
@Component
public class PresenceChannelInterceptor extends ChannelInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(PresenceChannelInterceptor.class);

	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
		// ignore non-STOMP messages like heartbeat messages
		if (sha.getCommand() == null) {
			return;
		}
	}

	// 连接成功
	private void connect(String sessionId, String accountId) {
		logger.debug(" STOMP Connect [sessionId: " + sessionId + "]");
		System.out.println("连接成功");
		MySessionSingleton.getInstance().setActiveUser(MySessionSingleton.getInstance().getActiveUser()+1);
	}

	// 断开连接
	private void disconnect(String sessionId, String accountId, StompHeaderAccessor sha) {
		logger.debug("STOMP Disconnect [sessionId: " + sessionId + "]");
		MySessionSingleton.getInstance().setActiveUser(MySessionSingleton.getInstance().getActiveUser()-1);
		System.out.println("断开连接");
	}

}