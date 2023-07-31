package com.tcloud.im.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * IM服务引导启动
 *
 * @author Anker
 */
@SpringBootApplication(scanBasePackages = "com.tcloud")
public class LemonImBootstrapApplication {

    public static void main(String[] args) {
        SpringApplication.run(LemonImBootstrapApplication.class, args);
    }

}
