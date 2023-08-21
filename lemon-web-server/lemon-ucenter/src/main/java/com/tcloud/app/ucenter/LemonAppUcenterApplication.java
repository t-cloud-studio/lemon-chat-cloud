package com.tcloud.app.ucenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.tcloud")
public class LemonAppUcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(LemonAppUcenterApplication.class, args);
    }

}
