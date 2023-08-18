package com.tcloud.im.server;

import com.tcloud.im.server.config.ImServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(ImServerConfig.class)
@EnableConfigurationProperties(ImServerConfig.class)
public class LemonImServerStarterAutoConfiguration {


}
