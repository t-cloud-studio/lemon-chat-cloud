package com.tcloud.web.auth.strategy.login.impl;

import com.tcloud.web.auth.domain.request.login.BaseLoginRequest;
import com.tcloud.web.auth.domain.request.login.CipherPairLoginRequest;
import com.tcloud.web.auth.domain.vo.LoginUser;
import com.tcloud.web.auth.strategy.login.AbstractLoginHandler;
import org.springframework.stereotype.Component;

@Component
public class CipherPairLoginHandler extends AbstractLoginHandler {

    @Override
    public LoginUser doLoginAction(BaseLoginRequest loginRequest) {
        CipherPairLoginRequest request = (CipherPairLoginRequest)loginRequest;
        return null;
    }
}
