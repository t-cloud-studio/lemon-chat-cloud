package com.tcloud.im.lsb.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BalancePolicyEnum {
    /**
     * 轮询
     * 不建议使用改负载算法，因为IM的特性，会出现频繁断开和连接的场景，可能导致某台服务器连接数过大，宕机
     */
    ROUND_ROBIN,
    /**
     * 权重
     */
    WEIGHT,
    /**
     * 随机
     */
    RANDOM,
    /**
     * 最小连接数
     */
    MIN_CONNECTION
    ;

}
