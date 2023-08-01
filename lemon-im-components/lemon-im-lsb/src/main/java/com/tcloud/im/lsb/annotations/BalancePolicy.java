package com.tcloud.im.lsb.annotations;

import com.tcloud.im.lsb.enums.BalancePolicyEnum;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface BalancePolicy {


    BalancePolicyEnum policy() default BalancePolicyEnum.ROUND_ROBIN;



}
