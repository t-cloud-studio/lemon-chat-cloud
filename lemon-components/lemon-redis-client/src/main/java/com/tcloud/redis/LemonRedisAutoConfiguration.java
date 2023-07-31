package com.tcloud.redis;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConfiguration;

@Configuration
@ConditionalOnClass(RedisProperties.class)
@EnableConfigurationProperties(RedisProperties.class)
public class LemonRedisAutoConfiguration implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("--- redis component is started ---");
    }
}
