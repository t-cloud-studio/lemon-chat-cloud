package com.tcloud.app.ucenter.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.tcloud.app.ucenter.domain.request.login.BaseLoginRequest;
import com.tcloud.app.ucenter.domain.vo.LoginUser;
import com.tcloud.app.ucenter.enums.LoginTypeEnum;
import com.tcloud.app.ucenter.service.LoginService;
import com.tcloud.web.common.exceptions.ApplicationBizException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {



    @Override
    public LoginUser login(BaseLoginRequest request) {
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.find(request.getLoginType());
        if (Objects.isNull(loginTypeEnum)){
            throw new ApplicationBizException("登录方式有误");
        }
        return SpringUtil.getBean(loginTypeEnum.getHandler()).doLoginAction(request);
    }
}
