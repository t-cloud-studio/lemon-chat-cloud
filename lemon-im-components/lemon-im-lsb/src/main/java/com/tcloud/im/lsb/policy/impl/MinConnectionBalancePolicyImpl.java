package com.tcloud.im.lsb.policy.impl;

import com.tcloud.im.lsb.annotations.BalancePolicy;
import com.tcloud.im.lsb.enums.BalancePolicyEnum;
import com.tcloud.im.lsb.policy.AbstractBalancePolicy;
import com.tcloud.register.domain.core.Server;

import java.util.List;

@BalancePolicy(policy = BalancePolicyEnum.RANDOM)
public class MinConnectionBalancePolicyImpl  extends AbstractBalancePolicy {

    @Override
    public Server balance() {
        if (super.getServers().isEmpty()) {
            return null;
        }
        List<Server> servers = super.getServers();
        return servers.stream().min(Server::compareTo).orElse(null);
    }
}
