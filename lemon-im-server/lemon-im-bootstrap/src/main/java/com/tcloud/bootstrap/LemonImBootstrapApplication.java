package com.tcloud.bootstrap;

import cn.hutool.extra.spring.SpringUtil;
import com.tcloud.session.server.ChatNettyServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LemonImBootstrapApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LemonImBootstrapApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        ChatNettyServer chatNettyServer = SpringUtil.getBean(ChatNettyServer.class);
        chatNettyServer.start();
    }
}
