package com.example.app.ucenter.strategy.login.impl;

import com.example.app.ucenter.domain.request.login.BaseLoginRequest;
import com.example.app.ucenter.domain.request.login.CipherPairLoginRequest;
import com.example.app.ucenter.domain.vo.LoginUser;
import com.example.app.ucenter.strategy.login.AbstractLoginHandler;
import org.springframework.stereotype.Component;

@Component
public class CipherPairLoginHandler extends AbstractLoginHandler {

    @Override
    public LoginUser doLoginAction(BaseLoginRequest loginRequest) {
        CipherPairLoginRequest request = (CipherPairLoginRequest)loginRequest;
        return null;
    }
}
