package com.tcloud.web.auth.strategy.impl;

import cn.hutool.core.lang.Assert;
import com.tcloud.common.obj.vo.UserInfoVO;
import com.tcloud.web.auth.domain.entity.UserInfo;
import com.tcloud.web.auth.domain.request.LoginRequest;
import com.tcloud.web.auth.service.UserInfoService;
import com.tcloud.web.auth.strategy.AbstractLoginHandler;
import com.tcloud.web.auth.utils.RequestUtil;
import com.tcloud.web.common.enums.BoolEnum;
import com.tcloud.web.common.exceptions.ApplicationBizException;
import com.tcloud.web.common.exceptions.BodyParamErrorException;
import com.tcloud.web.common.utils.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class CipherPairLoginHandler extends AbstractLoginHandler {


    @Autowired
    private UserInfoService userInfoService;

    @Override
    public UserInfoVO accountCheckout(LoginRequest loginRequest) {
        String phone = Optional.ofNullable(loginRequest.getIdentityKey()).orElseThrow(() -> new BodyParamErrorException("用户名或密码错误"));
        UserInfo userInfo = userInfoService.selectUserInfoByPhone(phone);
        if (Objects.isNull(userInfo)){
            ApplicationBizException.throwException("请注册后再登录");
        }
        if (BoolEnum.isTrue(userInfo.getStatus())){
            super.loginFail();
            ApplicationBizException.throwException("账号已被系统禁用");
        }
        if (BoolEnum.isTrue(userInfo.getDeleted())){
            super.loginFail();
            ApplicationBizException.throwException("账号注销");
        }
        String cipher = Optional.ofNullable(loginRequest.getCipher()).orElseThrow(() -> new BodyParamErrorException("用户名或密码错误"));
        String encryptCipher = EncryptUtil.md5Encrypt(cipher, userInfo.getCipherSalt());
        Assert.isTrue(userInfo.getCipher().equals(encryptCipher), "账号或密码错误");

        return this.buildUserVO(userInfo);
    }

    private UserInfoVO buildUserVO(UserInfo userInfo) {
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(userInfo.getId());
        userInfoVO.setLoginIp(RequestUtil.getIp());
        userInfoVO.setNickname(userInfo.getNickname());
        userInfoVO.setAvatar(userInfo.getAvatar());
        userInfoVO.setGender(userInfo.getGender());
        userInfoVO.setPersonalProfile(userInfo.getPersonalProfile());
        userInfoVO.setPlatform(RequestUtil.getRequestPlatform());
        return userInfoVO;
    }
}
