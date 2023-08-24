package com.tcloud.im.server.config;

import com.tcloud.redis.config.RedisClient;
import com.tcloud.register.handler.client.ClientRelationMapRegister;
import com.tcloud.register.handler.server.ServerRegister;
import com.tcloud.zkclient.cli.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LemonRegisterConfiguration {

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private RedisClient redisClient;

    @Bean
    @ConditionalOnMissingBean(ClientRelationMapRegister.class)
    public ClientRelationMapRegister clientRelationMapRegister(){
        return new ClientRelationMapRegister(zkClient, redisClient);
    }


    @Bean
    @ConditionalOnMissingBean(ServerRegister.class)
    public ServerRegister serverRegister(){
        return new ServerRegister(zkClient);
    }


}
