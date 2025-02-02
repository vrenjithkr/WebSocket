package com.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/websocket")
public class WebSocketClientController {

	@Autowired
	private WebSocketClientService clientService;

	@PostMapping("/connect")
	public String connect(@RequestParam String mobileNumber) {
		clientService.connect(mobileNumber);
		return "Connected to WebSocket with mobile number: " + mobileNumber;
	}

	@PostMapping("/addListener")
	public String addListener(@RequestParam String listenerId) {
		clientService.addListener(listenerId, message -> {
			System.out.println("Listener " + listenerId + " received message: " + message);
		});
		return "Listener added for: " + listenerId;
	}

	@PostMapping("/send")
	public String sendMessage(@RequestBody MessageData messageData) {
		clientService.sendMessage(messageData.getFrom(), messageData.getTo(), messageData.getContent());
		return "Message Sent: from=" + messageData.getFrom() + ", to=" + messageData.getTo() + ", content="
				+ messageData.getContent();
	}
}
