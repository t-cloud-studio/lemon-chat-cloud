package com.tcloud.im.lsb.config;

import cn.hutool.extra.spring.SpringUtil;
import com.tcloud.im.lsb.annotations.BalancePolicy;
import com.tcloud.im.lsb.policy.AbstractBalancePolicy;
import com.tcloud.im.lsb.policy.impl.MinConnectionBalancePolicyImpl;
import com.tcloud.register.domain.ServerInfo;
import com.tcloud.register.manager.server.ServerManager;
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
    private ImBalanceConfiguration balanceConfiguration;

    @PostConstruct
    @ConditionalOnBean(AbstractBalancePolicy.class)
    public void initPolicy(){
        List<ServerInfo> allServers = serverManager.getAllServers();
        AbstractBalancePolicy.setServers(allServers);
    }

    /**
     * 加载负载策略
     * @return 负载均衡策略
     */
    @Bean
    public AbstractBalancePolicy balancePolicy(){
        return policies.stream().filter(p -> {
            BalancePolicy annotation = p.getClass().getAnnotation(BalancePolicy.class);
            return balanceConfiguration.getPolicy().equals(annotation.policy());
        }).findAny().orElse(SpringUtil.getBean(MinConnectionBalancePolicyImpl.class));
    }




}
