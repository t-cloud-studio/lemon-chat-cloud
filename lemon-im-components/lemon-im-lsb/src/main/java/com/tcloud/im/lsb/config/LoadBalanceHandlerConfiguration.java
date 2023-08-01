package com.tcloud.im.lsb.config;

import com.tcloud.im.lsb.policy.AbstractBalancePolicy;
import com.tcloud.register.domain.core.Server;
import com.tcloud.register.handler.ServerManager;
import com.tcloud.zkclient.cli.ZkClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnBean(ZkClient.class)
public class LoadBalanceHandlerConfiguration {

    @Autowired
    private ServerManager serverManager;

    @PostConstruct
    public void initPolicy(){
        List<Server> allServers = serverManager.getAllServers();
        AbstractBalancePolicy.setServers(allServers);
    }





}
