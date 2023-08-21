package com.tcloud.im.lsb.policy.impl;

import cn.hutool.core.collection.CollUtil;
import com.tcloud.im.lsb.annotations.BalancePolicy;
import com.tcloud.im.lsb.enums.BalancePolicyEnum;
import com.tcloud.im.lsb.exceptions.NotAvailableServerException;
import com.tcloud.im.lsb.policy.AbstractBalancePolicy;
import com.tcloud.register.domain.ServerInfo;

import java.util.List;

@BalancePolicy(policy = BalancePolicyEnum.RANDOM)
public class MinConnectionBalancePolicyImpl  extends AbstractBalancePolicy {

    @Override
    public ServerInfo balance() {
        List<ServerInfo> servers = super.getServers();
        if (CollUtil.isEmpty(servers)){
            throw new NotAvailableServerException("no available server!");
        }
        return servers.stream().min(ServerInfo::compareTo).orElse(null);
    }
}
