package com.tcloud.session.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConditionalOnMissingBean
@ConfigurationProperties(prefix = "chat-server")
public class ChatServerConfig {


    /**
     * start on port
     */
    private Integer port;

    /**
     * the work group threads count , default value is the cpu count * 2
     */
    private Integer workGroupCount = Runtime.getRuntime().availableProcessors() * 2;



}
