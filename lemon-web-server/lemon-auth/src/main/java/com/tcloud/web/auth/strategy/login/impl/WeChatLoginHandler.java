package com.tcloud.web.auth.strategy.login.impl;

import com.tcloud.web.auth.domain.request.login.BaseLoginRequest;
import com.tcloud.web.auth.domain.request.login.WeChatLoginRequest;
import com.tcloud.web.auth.domain.vo.LoginUser;
import com.tcloud.web.auth.strategy.login.AbstractLoginHandler;
import org.springframework.stereotype.Component;

@Component
public class WeChatLoginHandler extends AbstractLoginHandler {


    @Override
    public LoginUser doLoginAction(BaseLoginRequest loginRequest) {
        WeChatLoginRequest request = (WeChatLoginRequest)loginRequest;
    
        return null;
    }
}
