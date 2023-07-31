package com.tcloud.im.gateway.config;

import cn.hutool.core.collection.CollUtil;
import com.tcloud.im.gateway.annotations.BalancePolicy;
import com.tcloud.im.gateway.policy.AbstractBalancePolicy;
import com.tcloud.im.gateway.policy.impl.RoundRobinBalancePolicyImpl;
import com.tcloud.register.domain.core.ClientSession;
import com.tcloud.register.domain.core.Server;
import com.tcloud.register.handler.ServerManager;
import com.tcloud.zkclient.cli.ZkClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
@ConditionalOnBean(ImGatewayConfiguration.class)
public class LoadBalanceHandlerConfiguration {


    private final ImGatewayConfiguration gatewayConfiguration;
    private final List<AbstractBalancePolicy> balancePolicies;

    private final ServerManager serverManager;


    @Bean
    public AbstractBalancePolicy balancePolicy(){
        AbstractBalancePolicy abstractBalancePolicy = balancePolicies.stream().filter(p -> {
            BalancePolicy annotation = p.getClass().getAnnotation(BalancePolicy.class);
            return annotation.policy().equals(gatewayConfiguration.getPolicy());
        }).findAny().orElse(null);
        if (Objects.isNull(abstractBalancePolicy)){
            throw new ApplicationContextException("please set you balance policy");
        }
        List<Server> allServers = serverManager.getAllServers();
        abstractBalancePolicy.setServers(allServers);
        if (abstractBalancePolicy instanceof RoundRobinBalancePolicyImpl roundRobinBalancePolicy){
            roundRobinBalancePolicy.setCurrentIndex(0);
        }
        return abstractBalancePolicy;
    }





}
