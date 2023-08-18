package com.tcloud.im.lsb.policy.impl;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.tcloud.im.lsb.annotations.BalancePolicy;
import com.tcloud.im.lsb.enums.BalancePolicyEnum;
import com.tcloud.im.lsb.exceptions.NotAvailableServerException;
import com.tcloud.im.lsb.policy.AbstractBalancePolicy;
import com.tcloud.register.domain.core.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@BalancePolicy(policy = BalancePolicyEnum.RANDOM)
public class MinConnectionBalancePolicyImpl  extends AbstractBalancePolicy {

    @Override
    public Server balance() {
        List<Server> servers = super.getServers();
        if (CollUtil.isEmpty(servers)){
            throw new NotAvailableServerException("no available server!");
        }
        return servers.stream().min(Server::compareTo).orElse(null);
    }
}
