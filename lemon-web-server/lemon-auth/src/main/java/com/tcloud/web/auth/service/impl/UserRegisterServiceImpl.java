package com.tcloud.web.auth.service.impl;

import cn.hutool.core.lang.Assert;
import com.tcloud.web.auth.domain.entity.UserInfo;
import com.tcloud.web.auth.domain.request.RegisterRequest;
import com.tcloud.web.auth.service.CaptchaActionService;
import com.tcloud.web.auth.service.UserInfoService;
import com.tcloud.web.auth.service.UserRegisterService;
import com.tcloud.web.common.constants.NumConstant;
import com.tcloud.web.common.exceptions.ApplicationBizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 注册
 *
 * @author Anker
 */
@Slf4j
@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    @Autowired
    private CaptchaActionService captchaActionService;
    @Autowired
    private UserInfoService userInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerAccount(RegisterRequest request) {
        // 手机验证码校验
        Assert.isTrue(captchaActionService.verificationSmsCaptcha(request.getPhone(), request.getCaptcha()), () -> new ApplicationBizException("验证码已失效!"));
        // 电话号码
        String phone = request.getPhone();
        UserInfo userInfo = userInfoService.existsPhone(phone);
        if (Objects.isNull(userInfo)) {
            userInfo = userInfoService.createUser(request.getPhone(), request.getCipher());
            log.info("user :{} register success", userInfo.getId());
            return;
        }
        // 被封禁
        if (userInfo.getStatus().equals(NumConstant.ONE)) {
            throw new ApplicationBizException("该号码已被管理员封禁,无法继续注册");
        }
    }

}
