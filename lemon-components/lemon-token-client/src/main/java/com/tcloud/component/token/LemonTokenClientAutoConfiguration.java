package com.tcloud.component.token;

import com.tcloud.component.token.config.SaTokenConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Token启动器
 *
 * @author Anker
 */
@Configuration
@ConditionalOnClass(SaTokenConfiguration.class)
@EnableConfigurationProperties(SaTokenConfiguration.class)
public class LemonTokenClientAutoConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(LemonTokenClientAutoConfiguration.class, args);
    }

}
