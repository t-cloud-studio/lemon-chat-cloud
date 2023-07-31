package com.tcloud.im.gateway.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BalancePolicyEnum {
    /**
     * 轮询
     */
    ROUND_ROBIN,
    /**
     * 权重
     */
    WEIGHT,
    /**
     * 随机
     */
    RANDOM;

}
