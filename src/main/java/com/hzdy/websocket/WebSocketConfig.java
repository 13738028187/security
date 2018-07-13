package com.hzdy.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/*@Configuration
// 开启对websocket的支持,使用stomp协议传输代理消息，
// 这时控制器使用@MessageMapping和@RequestMaping一样
@EnableWebSocketMessageBroker*/
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	/**
	 * 服务器要监听的端口，message会从这里进来，要对这里加一个Handler 这样在网页中就可以通过websocket连接上服务了
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
		// 注册stomp的节点，映射到指定的url,并指定使用sockjs协议
		stompEndpointRegistry.addEndpoint("/contactChatSocket").withSockJS();
	}

	// 配置消息代理
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// queue、topic、user代理
		registry.enableSimpleBroker("/queue", "/topic", "/user");
		registry.setUserDestinationPrefix("/user/");
	}

	/**
	 * 消息传输参数配置
	 */
	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		registry.setMessageSizeLimit(8192) // 设置消息字节数大小
				.setSendBufferSizeLimit(8192)// 设置消息缓存大小
				.setSendTimeLimit(10000); // 设置消息发送时间限制毫秒
	}

	/**
	 * 输入通道参数设置
	 */
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.taskExecutor().corePoolSize(4) // 设置消息输入通道的线程池线程数
				.maxPoolSize(8)// 最大线程数
				.keepAliveSeconds(60);// 线程活动时间
		registration.setInterceptors(presenceChannelInterceptor());
	}

	/**
	 * 输出通道参数设置
	 */
	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		registration.taskExecutor().corePoolSize(4).maxPoolSize(8);
		registration.setInterceptors(presenceChannelInterceptor());
	}
	@Bean
	public PresenceChannelInterceptor presenceChannelInterceptor() {
		return new PresenceChannelInterceptor();
	}

}