package com.ws;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Service
public class WebSocketClientService {

	private WebSocketSession session;
	private final Map<String, MessageListener> listeners = new ConcurrentHashMap<>();

	/**
	 * Connect to WebSocket server dynamically using the mobile number.
	 *
	 * @param mobileNumber The mobile number to include in the WebSocket connection
	 *                     URL.
	 */
	public void connect(String mobileNumber) {
		StandardWebSocketClient client = new StandardWebSocketClient();
		try {
			String url = "ws://localhost:8080/ws?mobileNumber=" + mobileNumber;

			this.session = client.doHandshake(new AbstractWebSocketHandler() {
				@Override
				public void handleTextMessage(WebSocketSession session, TextMessage message) {
					handleIncomingMessage(message.getPayload());
				}
			}, url).get();

			System.out.println("Connected to WebSocket with mobile number: " + mobileNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add a listener for incoming messages.
	 *
	 * @param listenerId Unique identifier for the listener.
	 * @param listener   The listener callback.
	 */
	public void addListener(String listenerId, MessageListener listener) {
		listeners.put(listenerId, listener);
		System.out.println("Listener added for: " + listenerId);
	}

	/**
	 * Handle incoming WebSocket messages and notify listeners.
	 *
	 * @param message The incoming WebSocket message.
	 */
	private void handleIncomingMessage(String message) {
		System.out.println("Received: " + message);
		for (Map.Entry<String, MessageListener> entry : listeners.entrySet()) {
			entry.getValue().onMessage(message);
		}
	}

	/**
	 * Send a message to the WebSocket server.
	 *
	 * @param from    Sender's identifier.
	 * @param to      Recipient's identifier.
	 * @param content The message content.
	 */
	public void sendMessage(String from, String to, String content) {
		if (session != null && session.isOpen()) {
			String payload = String.format("{\"from\":\"%s\",\"to\":\"%s\",\"content\":\"%s\"}", from, to, content);
			try {
				session.sendMessage(new TextMessage(payload));
				System.out.println("Sent: " + payload);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Session is not open.");
		}
	}
}
