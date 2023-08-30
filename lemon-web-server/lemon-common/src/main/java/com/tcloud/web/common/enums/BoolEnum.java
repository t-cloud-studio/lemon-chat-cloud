package com.tcloud.web.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BoolEnum {

    FALSE(0, Boolean.FALSE),


    TRUE(1, Boolean.TRUE)
    ;


    private final Integer var;


    private final Boolean res;

    public static BoolEnum load(Integer var){
        return Arrays.stream(values()).filter(b -> b.var.equals(var)).findFirst().orElse(FALSE);
    }

    public static Boolean isOne(Integer var){
        return load(var).res.equals(Boolean.TRUE);
    }

    public static Boolean isZero(Integer var){
        return !isTrue(var);
    }

    public static Boolean isTrue(Integer var){
        return load(var).res.equals(Boolean.TRUE);
    }

    public static Boolean isFalse(Integer var){
        return !isTrue(var);
    }


}
