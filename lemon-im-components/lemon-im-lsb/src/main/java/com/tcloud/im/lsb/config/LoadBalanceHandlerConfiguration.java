package com.tcloud.im.lsb.config;

import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import com.tcloud.im.lsb.annotations.BalancePolicy;
import com.tcloud.im.lsb.policy.AbstractBalancePolicy;
import com.tcloud.im.lsb.policy.impl.RoundRobinBalancePolicyImpl;
import com.tcloud.register.domain.core.Server;
import com.tcloud.register.handler.ServerManager;
import com.tcloud.zkclient.cli.ZkClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnBean(ZkClient.class)
public class LoadBalanceHandlerConfiguration {

    @Autowired
    private ServerManager serverManager;

    @Autowired
    private List<AbstractBalancePolicy> policies;
    @Autowired
    private ImGatewayConfiguration gatewayConfiguration;

    @PostConstruct
    public void initPolicy(){
        List<Server> allServers = serverManager.getAllServers();
        AbstractBalancePolicy.setServers(allServers);
    }

    /**
     * 加载负载策略
     * @return
     */
    @Bean
    public AbstractBalancePolicy balancePolicy(){
        return policies.stream().filter(p -> {
            BalancePolicy annotation = p.getClass().getAnnotation(BalancePolicy.class);
            return gatewayConfiguration.getPolicy().equals(annotation.policy());
        }).findAny().orElse(SpringUtil.getBean(RoundRobinBalancePolicyImpl.class));
    }




}
