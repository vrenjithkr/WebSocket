package com.ws;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@SpringBootApplication
public class WsClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsClientApplication.class, args);
	}

	@Bean
	public WebSocketStompClient stompClient() {
		List<Transport> transports = Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()));
		return new WebSocketStompClient(new SockJsClient(transports));
	}
}
