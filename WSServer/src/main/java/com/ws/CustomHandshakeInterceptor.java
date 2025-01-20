package com.ws;

import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;

import java.util.Map;

public class CustomHandshakeInterceptor implements HandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		// Extract the "mobileNumber" from the query parameters
		String query = request.getURI().getQuery();
		if (query != null && query.contains("mobileNumber=")) {
			String mobileNumber = query.split("mobileNumber=")[1];
			attributes.put("mobileNumber", mobileNumber);
		} else {
			System.out.println("Connection rejected: mobileNumber parameter missing");
			return false; // Reject the handshake if mobileNumber is not present
		}
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// No-op
	}
}
