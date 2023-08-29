package com.tcloud.web.auth.strategy.impl;

import com.tcloud.web.auth.domain.request.LoginRequest;
import com.tcloud.common.obj.vo.UserInfoVO;
import com.tcloud.web.auth.strategy.AbstractLoginHandler;
import com.tcloud.web.auth.utils.IPUtil;
import com.tcloud.web.common.exceptions.BodyParamErrorException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CipherPairLoginHandler extends AbstractLoginHandler {

    @Override
    public UserInfoVO accountCheckout(LoginRequest loginRequest) {
        String cipher = Optional.ofNullable(loginRequest.getCipher()).orElseThrow(() -> new BodyParamErrorException("用户名或密码错误"));
        String identityKey = Optional.ofNullable(loginRequest.getIdentityKey()).orElseThrow(() -> new BodyParamErrorException("用户名或密码错误"));
        if (loginRequest.getCipher().equals("123") && loginRequest.getIdentityKey().equals("123")){
            UserInfoVO userInfoVO = new UserInfoVO();
            userInfoVO.setId(1L);
            userInfoVO.setLoginIp(IPUtil.getIp());
            userInfoVO.setNickname("哇咔咔");
            return userInfoVO;
        }
        return null;
    }
}
