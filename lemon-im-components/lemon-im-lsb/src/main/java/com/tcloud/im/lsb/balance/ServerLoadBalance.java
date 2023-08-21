package com.tcloud.im.lsb.balance;

import com.tcloud.im.lsb.policy.AbstractBalancePolicy;
import com.tcloud.register.domain.ServerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerLoadBalance {

    private final AbstractBalancePolicy balancePolicy;


    @Autowired
    public ServerLoadBalance(AbstractBalancePolicy balancePolicy) {
        this.balancePolicy = balancePolicy;
    }

    /**
     * 执行负载均衡策略
     *
     * @return 负载后的服务信息
     */
    public ServerInfo balance() {
        return balancePolicy.balance();
    }
}
