package com.tcloud.im.gateway.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author evans
 */
@SpringBootApplication(scanBasePackages = "com.tcloud")
public class LemonGatewayWebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(LemonGatewayWebsocketApplication.class, args);
	}

}
