package com.tcloud.app.ucenter.strategy.login.impl;

import com.tcloud.app.ucenter.domain.request.login.BaseLoginRequest;
import com.tcloud.app.ucenter.domain.request.login.CipherPairLoginRequest;
import com.tcloud.app.ucenter.domain.vo.LoginUser;
import com.tcloud.app.ucenter.strategy.login.AbstractLoginHandler;
import org.springframework.stereotype.Component;

@Component
public class CipherPairLoginHandler extends AbstractLoginHandler {

    @Override
    public LoginUser doLoginAction(BaseLoginRequest loginRequest) {
        CipherPairLoginRequest request = (CipherPairLoginRequest)loginRequest;
        return null;
    }
}
