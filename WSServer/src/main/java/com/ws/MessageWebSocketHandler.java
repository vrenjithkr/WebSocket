package com.ws;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

public class MessageWebSocketHandler extends TextWebSocketHandler {

	private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String mobileNumber = (String) session.getAttributes().get("mobileNumber");
		if (mobileNumber == null) {
			System.out.println("Connection rejected: mobileNumber is null");
			session.close();
			return;
		}
		sessions.put(mobileNumber, session);
		System.out.println("Connection established for mobileNumber: " + mobileNumber);
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		MessageData data = new ObjectMapper().readValue(payload, MessageData.class);

		System.out.println("Routing message: " + payload + " to " + data.getTo());
		WebSocketSession recipientSession = sessions.get(data.getTo());
		if (recipientSession != null && recipientSession.isOpen()) {
			recipientSession.sendMessage(new TextMessage(payload));
			System.out.println("Message sent to recipient: " + data.getTo());
		} else {
			System.out.println("Recipient session not found or closed for: " + data.getTo());
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.values().remove(session);
	}
}