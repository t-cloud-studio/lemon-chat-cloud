package com.tcloud.im.lsb.balance;

import cn.hutool.extra.spring.SpringUtil;
import com.tcloud.im.lsb.annotations.BalancePolicy;
import com.tcloud.im.lsb.config.ImGatewayConfiguration;
import com.tcloud.im.lsb.policy.AbstractBalancePolicy;
import com.tcloud.im.lsb.policy.impl.RoundRobinBalancePolicyImpl;
import com.tcloud.register.domain.core.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoadBalanceHandler {

    @Autowired
    private List<AbstractBalancePolicy> policies;
    @Autowired
    private ImGatewayConfiguration gatewayConfiguration;

    public Server balance() {
        AbstractBalancePolicy policy = policies.stream().filter(p -> {
            BalancePolicy annotation = p.getClass().getAnnotation(BalancePolicy.class);
            return gatewayConfiguration.getPolicy().equals(annotation.policy());
        }).findAny().orElse(SpringUtil.getBean(RoundRobinBalancePolicyImpl.class));
        return policy.balance();
    }
}
