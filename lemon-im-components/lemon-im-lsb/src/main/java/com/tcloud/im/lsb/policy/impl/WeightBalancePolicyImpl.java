package com.tcloud.im.lsb.policy.impl;

import com.tcloud.im.lsb.annotations.BalancePolicy;
import com.tcloud.im.lsb.enums.BalancePolicyEnum;
import com.tcloud.im.lsb.policy.AbstractBalancePolicy;
import com.tcloud.register.domain.ServerInfo;

import java.util.Random;

/**
 * 权重算法
 *
 * @author Anker
 */
@BalancePolicy(policy = BalancePolicyEnum.WEIGHT)
public class WeightBalancePolicyImpl extends AbstractBalancePolicy {



    @Override
    public ServerInfo balance() {
        if (super.getServers().isEmpty()) {
            return null;
        }
        // 计算总权重
        int totalWeight = 0;
        for (ServerInfo server : super.getServers()) {
            totalWeight += server.getConnections().intValue();
        }
        // 根据权重选择服务器
        ServerInfo nextServer = null;
        int accumulatedWeight = 0;
        int randomWeight = new Random().nextInt(totalWeight);
        for (ServerInfo server : super.getServers()) {
            accumulatedWeight += server.getConnections().intValue();
            if (randomWeight < accumulatedWeight) {
                nextServer = server;
                break;
            }
        }
        return nextServer;
    }
}
