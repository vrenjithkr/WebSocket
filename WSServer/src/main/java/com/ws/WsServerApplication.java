package com.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WsServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsServerApplication.class, args);
	}

}
