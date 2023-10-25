package com.tcloud.components.mq;

import com.tcloud.components.mq.config.RocketMQConfigProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass(RocketMQConfigProperties.class)
@EnableConfigurationProperties(RocketMQConfigProperties.class)
public class LemonMqClientAutoConfiguration implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("--- RocketMQ is auto configuration completed!");
    }
}