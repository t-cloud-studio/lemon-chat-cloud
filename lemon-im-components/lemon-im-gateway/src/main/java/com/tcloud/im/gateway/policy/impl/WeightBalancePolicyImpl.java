package com.tcloud.im.gateway.policy.impl;

import com.tcloud.im.gateway.annotations.BalancePolicy;
import com.tcloud.im.gateway.enums.BalancePolicyEnum;
import com.tcloud.im.gateway.policy.AbstractBalancePolicy;
import com.tcloud.register.domain.core.Server;

import java.util.Random;

/**
 * 权重算法
 *
 * @author Anker
 */
@BalancePolicy(policy = BalancePolicyEnum.WEIGHT)
public class WeightBalancePolicyImpl extends AbstractBalancePolicy {



    @Override
    public Server balance() {
        if (super.getServers().isEmpty()) {
            return null;
        }
        // 计算总权重
        int totalWeight = 0;
        for (Server server : super.getServers()) {
            totalWeight += server.getWeight();
        }
        // 根据权重选择服务器
        Server nextServer = null;
        int accumulatedWeight = 0;
        int randomWeight = new Random().nextInt(totalWeight);
        for (Server server : super.getServers()) {
            accumulatedWeight += server.getWeight();
            if (randomWeight < accumulatedWeight) {
                nextServer = server;
                break;
            }
        }
        return nextServer;
    }
}
