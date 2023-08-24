package com.tcloud.web.auth.strategy.impl;

import com.tcloud.web.auth.domain.request.LoginRequest;
import com.tcloud.web.auth.domain.vo.UserInfoVO;
import com.tcloud.web.auth.strategy.AbstractLoginHandler;
import org.springframework.stereotype.Component;

@Component
public class MobilePhoneCaptchaLoginHandler extends AbstractLoginHandler {


    @Override
    public UserInfoVO accountCheckout(LoginRequest loginRequest) {
        return null;
    }
}
