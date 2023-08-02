package com.tcloud.zkclient;

import com.tcloud.zkclient.config.ZkConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * zk自动装配
 *
 * @author Anker
 */
@Configuration
@ConditionalOnClass(ZkConfiguration.class)
@EnableConfigurationProperties(ZkConfiguration.class)
public class LemonZookeeperAutoConfiguration implements InitializingBean {


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("--- ZookeeperAutoConfiguration is complete!");
    }
}
