package com.tcloud.im.lsb;


import com.tcloud.im.lsb.config.ImBalanceConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(ImBalanceConfiguration.class)
public class LemonImGatewayAutoConfiguration implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("--- im gateway is started!!");
    }
}
