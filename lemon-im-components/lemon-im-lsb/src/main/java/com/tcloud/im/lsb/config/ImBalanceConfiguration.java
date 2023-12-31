package com.tcloud.im.lsb.config;

import com.tcloud.im.lsb.enums.BalancePolicyEnum;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 *IM 网关配置
 *
 * @author Anker
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "im.balance.policy")
public class ImBalanceConfiguration implements InitializingBean {

    /**
     * 路由策略
     */
    private BalancePolicyEnum policy;
    /**
     * 权重
     */
    private Integer weight;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (BalancePolicyEnum.WEIGHT.equals(policy) && Objects.isNull(weight)){
            throw new NullPointerException("please set the weight");
        }
    }
}
