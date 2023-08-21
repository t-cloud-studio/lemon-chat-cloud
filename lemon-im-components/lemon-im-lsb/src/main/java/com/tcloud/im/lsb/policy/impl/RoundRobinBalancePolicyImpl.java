package com.tcloud.im.lsb.policy.impl;

import com.tcloud.im.common.constants.NumConstant;
import com.tcloud.im.lsb.annotations.BalancePolicy;
import com.tcloud.im.lsb.enums.BalancePolicyEnum;
import com.tcloud.im.lsb.policy.AbstractBalancePolicy;
import com.tcloud.register.domain.ServerInfo;
import lombok.Data;

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
    private Integer currentIndex = NumConstant.ZERO;

    @Override
    public ServerInfo balance() {
        if (super.getServers().isEmpty()) {
            return null;
        }
        ServerInfo nextServer = super.getServers().get(currentIndex);
        currentIndex = (currentIndex + 1) % super.getServers().size();
        return nextServer;
    }
}
