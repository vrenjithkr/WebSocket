package com.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
class WebSocketConfig implements WebSocketConfigurer {

	@Autowired
	private MessageWebSocketHandler messageWebSocketHandler;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(messageWebSocketHandler, "/ws").setAllowedOrigins("*")
				.addInterceptors(new CustomHandshakeInterceptor());
	}
}
