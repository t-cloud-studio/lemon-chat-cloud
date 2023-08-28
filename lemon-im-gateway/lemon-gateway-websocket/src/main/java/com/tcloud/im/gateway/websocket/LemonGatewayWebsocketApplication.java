package com.tcloud.im.gateway.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @author evans
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.tcloud")
public class LemonGatewayWebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(LemonGatewayWebsocketApplication.class, args);
	}

}
