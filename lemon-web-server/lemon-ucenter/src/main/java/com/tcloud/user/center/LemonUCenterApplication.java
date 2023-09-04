package com.tcloud.user.center;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@MapperScan(basePackages = {"com.tcloud.user.center.mapper"})
@SpringBootApplication(scanBasePackages = "com.tcloud.user.center")
public class LemonUCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(LemonUCenterApplication.class, args);
    }

}
