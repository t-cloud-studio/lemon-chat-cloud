package com.tcloud.im.gateway.annotations;

import com.tcloud.im.gateway.enums.BalancePolicyEnum;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface BalancePolicy {


    BalancePolicyEnum policy() default BalancePolicyEnum.POLLING;



}
