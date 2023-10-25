package com.tcloud.components.mq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rocketmq")
public class RocketMQConfigProperties {

    /**
     * 命名服务地址
     */
    private String nameServerAddr;
    /**
     * 生产者组
     */
    private String producerGroup;
}
