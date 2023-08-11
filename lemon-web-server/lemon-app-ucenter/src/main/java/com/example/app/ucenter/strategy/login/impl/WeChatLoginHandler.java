package com.example.app.ucenter.strategy.login.impl;

import com.example.app.ucenter.domain.request.login.BaseLoginRequest;
import com.example.app.ucenter.domain.request.login.WeChatLoginRequest;
import com.example.app.ucenter.domain.vo.LoginUser;
import com.example.app.ucenter.strategy.login.AbstractLoginHandler;
import org.springframework.stereotype.Component;

@Component
public class WeChatLoginHandler extends AbstractLoginHandler {


    @Override
    public LoginUser doLoginAction(BaseLoginRequest loginRequest) {
        WeChatLoginRequest request = (WeChatLoginRequest)loginRequest;

        return null;
    }
}
