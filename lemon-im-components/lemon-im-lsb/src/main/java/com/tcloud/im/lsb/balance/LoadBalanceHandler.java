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

    private final AbstractBalancePolicy balancePolicy;


    @Autowired
    public LoadBalanceHandler(AbstractBalancePolicy balancePolicy) {
        this.balancePolicy = balancePolicy;
    }

    /**
     * 执行负载均衡策略
     *
     * @return 负载后的服务信息
     */
    public Server balance() {
        return balancePolicy.balance();
    }
}
