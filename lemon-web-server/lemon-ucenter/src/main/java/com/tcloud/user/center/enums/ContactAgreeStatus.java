package com.tcloud.user.center.enums;

import com.tcloud.user.center.strategy.IAgreeEventProgress;
import com.tcloud.user.center.strategy.impl.AgreeEventProcessHandler;
import com.tcloud.user.center.strategy.impl.BlockEventProcessHandler;
import com.tcloud.user.center.strategy.impl.DisagreeEventProcessHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ContactAgreeStatus {


    AGREED(1, "已同意", AgreeEventProcessHandler.class),

    NOT_RESPONSE_YET(0, "暂无回应", null),

    DISAGREE(-1 ,"拒绝申请", DisagreeEventProcessHandler.class),

    BLOCK(999, "已被对方加入黑名单", BlockEventProcessHandler.class);


    private final Integer status;

    private final String describe;

    private final Class<? extends IAgreeEventProgress> handler;


    public static ContactAgreeStatus load(Integer status){
        return Arrays.stream(values()).filter(c -> status.equals(c.getStatus())).findFirst().orElse(null);
    }

    public static boolean isAgreed(Integer status){
        return AGREED.equals(load(status));
    }

    public static boolean isBlock(Integer status){
        return BLOCK.equals(load(status));
    }

    public static boolean disagreed(Integer status){
        return DISAGREE.equals(load(status));
    }

}
