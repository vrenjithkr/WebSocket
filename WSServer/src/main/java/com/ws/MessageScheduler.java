package com.ws;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
class MessageScheduler {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private MessageWebSocketHandler handler;

	@Scheduled(fixedDelay = 30000) // Check every 30 seconds
	public void resendUnreadMessages() {
		System.out.println("Checking for unread messages");
		List<UserMessages> unreadMessages = messageRepository.findByStatus("unread");

		for (UserMessages message : unreadMessages) {
			WebSocketSession session = handler.getSessionForUser(message.getToUser());
			if (session != null && session.isOpen()) {
				try {
					session.sendMessage(new TextMessage(message.getContent()));
					message.setStatus("read");
					message.setReceiveTime(LocalDateTime.now());
					messageRepository.save(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}