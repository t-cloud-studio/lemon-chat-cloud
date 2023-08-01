package com.tcloud.im.lsb.policy.impl;

import com.tcloud.im.lsb.annotations.BalancePolicy;
import com.tcloud.im.lsb.enums.BalancePolicyEnum;
import com.tcloud.im.lsb.policy.AbstractBalancePolicy;
import com.tcloud.register.domain.core.Server;

import java.util.Random;

/**
 * 随机负载
 *
 * @author Anker
 */
@BalancePolicy(policy = BalancePolicyEnum.RANDOM)
public class RandomBalancePolicyImpl extends AbstractBalancePolicy {

    @Override
    public Server balance() {
        if (super.getServers().isEmpty()) {
            return null;
        }
        int randomIndex = new Random().nextInt(super.getServers().size());
        return super.getServers().get(randomIndex);
    }
}
