package com.tcloud.im.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.tcloud")
public class LemonImServerBootstrapApplication {

    public static void main(String[] args) {
        SpringApplication.run(LemonImServerBootstrapApplication.class, args);
    }

}
