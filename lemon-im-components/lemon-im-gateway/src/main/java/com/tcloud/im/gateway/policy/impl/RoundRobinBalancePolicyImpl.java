package com.tcloud.im.gateway.policy.impl;

import com.tcloud.im.gateway.annotations.BalancePolicy;
import com.tcloud.im.gateway.enums.BalancePolicyEnum;
import com.tcloud.im.gateway.policy.AbstractBalancePolicy;
import com.tcloud.register.domain.core.Server;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 轮询算法
 *
 * @author Anker
 */

@Data
@BalancePolicy(policy = BalancePolicyEnum.ROUND_ROBIN)
public class RoundRobinBalancePolicyImpl extends AbstractBalancePolicy {

    /**
     * 当前索引位置
     */
    private Integer currentIndex;

    public RoundRobinBalancePolicyImpl(Integer currentIndex) {
        this.currentIndex = currentIndex;
    }

    @Override
    public Server balance() {
        if (super.getServers().isEmpty()) {
            return null;
        }
        Server nextServer = super.getServers().get(currentIndex);
        currentIndex = (currentIndex + 1) % super.getServers().size();
        return nextServer;
    }
}
