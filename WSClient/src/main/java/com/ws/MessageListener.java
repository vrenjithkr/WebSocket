package com.ws;

@FunctionalInterface
public interface MessageListener {
	void onMessage(String message);
}
