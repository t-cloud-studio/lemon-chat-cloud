package com.tcloud.im.gateway.websocket.config;

import com.tcloud.redis.config.RedisClient;
import com.tcloud.register.handler.client.ClientRelationMapRegister;
import com.tcloud.register.handler.server.ServerRegister;
import com.tcloud.zkclient.cli.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketApplicationConfiguration {

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private RedisClient redisClient;

    @Bean
    public ClientRelationMapRegister clientRelationMapRegister(){
        return new ClientRelationMapRegister(zkClient, redisClient);
    }


    @Bean
    public ServerRegister serverRegister(){
        return new ServerRegister(zkClient);
    }


}
