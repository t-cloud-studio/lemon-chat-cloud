package com.tcloud.im.gateway.config;

import com.tcloud.im.gateway.enums.BalancePolicyEnum;
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
@ConfigurationProperties(prefix = "im.gateway")
public class ImGatewayConfiguration implements InitializingBean {

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
        if (Objects.isNull(policy)){
            policy = BalancePolicyEnum.ROUND_ROBIN;
        }
        if (BalancePolicyEnum.WEIGHT.equals(policy) && Objects.isNull(weight)){
            throw new NullPointerException("please set the weight");
        }
    }
}
