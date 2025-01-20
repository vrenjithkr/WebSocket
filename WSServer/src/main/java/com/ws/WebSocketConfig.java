package com.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
class WebSocketConfig implements WebSocketConfigurer {
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new MessageWebSocketHandler(), "/ws").setAllowedOrigins("*")
				.addInterceptors(new CustomHandshakeInterceptor());
	}
}
