package com.tcloud.web.auth.enums;

import com.tcloud.web.auth.strategy.AbstractLoginHandler;
import com.tcloud.web.auth.strategy.impl.CipherPairLoginHandler;
import com.tcloud.web.auth.strategy.impl.MobilePhoneCaptchaLoginHandler;
import com.tcloud.web.auth.strategy.impl.WeChatLoginHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum LoginTypeEnum {


    CIPHER_PAIR(1, CipherPairLoginHandler.class),

    MOBILE_CAPTCHA(2, MobilePhoneCaptchaLoginHandler.class),

    WE_CHAT(3, WeChatLoginHandler.class);


    private final Integer code;

    private final Class<? extends AbstractLoginHandler> handler;

    public static LoginTypeEnum find(Integer code){
        return Arrays.stream(values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
    }


}
