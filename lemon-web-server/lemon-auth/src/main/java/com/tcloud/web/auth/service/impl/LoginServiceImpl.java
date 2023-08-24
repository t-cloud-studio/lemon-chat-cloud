package com.tcloud.web.auth.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.tcloud.web.auth.domain.request.LoginRequest;
import com.tcloud.web.auth.domain.vo.LoginResponse;
import com.tcloud.web.auth.enums.LoginTypeEnum;
import com.tcloud.web.auth.service.LoginService;
import com.tcloud.web.common.exceptions.ApplicationBizException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {



    @Override
    public LoginResponse login(LoginRequest request) {
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.find(request.getLoginType());
        if (Objects.isNull(loginTypeEnum)){
            throw new ApplicationBizException("登录方式有误");
        }
        return SpringUtil.getBean(loginTypeEnum.getHandler()).loginAction(request);
    }
}
