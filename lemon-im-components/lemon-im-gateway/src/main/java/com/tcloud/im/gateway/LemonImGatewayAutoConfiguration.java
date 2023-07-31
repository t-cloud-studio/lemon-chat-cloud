package com.tcloud.im.gateway;


import com.tcloud.im.gateway.config.ImGatewayConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(ImGatewayConfiguration.class)
@EnableConfigurationProperties(ImGatewayConfiguration.class)
public class LemonImGatewayAutoConfiguration implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("im gateway is started!!");
    }
}
