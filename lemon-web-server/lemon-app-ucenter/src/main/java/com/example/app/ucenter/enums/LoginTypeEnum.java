package com.example.app.ucenter.enums;

import com.example.app.ucenter.strategy.login.AbstractLoginHandler;
import com.example.app.ucenter.strategy.login.impl.CipherPairLoginHandler;
import com.example.app.ucenter.strategy.login.impl.MobilePhoneCaptchaLoginHandler;
import com.example.app.ucenter.strategy.login.impl.WeChatLoginHandler;
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
