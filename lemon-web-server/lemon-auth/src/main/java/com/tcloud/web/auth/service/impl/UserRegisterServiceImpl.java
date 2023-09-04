package com.tcloud.web.auth.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.tcloud.feign.user.api.UserInfoFeignService;
import com.tcloud.web.auth.domain.entity.UserInfo;
import com.tcloud.web.auth.domain.request.RegisterRequest;
import com.tcloud.web.auth.service.CaptchaActionService;
import com.tcloud.web.auth.service.UserInfoService;
import com.tcloud.web.auth.service.UserRegisterService;
import com.tcloud.web.common.constants.NumConstant;
import com.tcloud.web.common.enums.BoolEnum;
import com.tcloud.web.common.exceptions.ApplicationBizException;
import com.tcloud.web.common.utils.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.tcloud.web.common.constants.CoreConstant.DEFAULT_NICKNAME_PREFIX;

/**
 * 注册
 *
 * @author Anker
 */
@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    @Autowired
    private CaptchaActionService captchaActionService;
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public void Register(RegisterRequest request) {
        // 手机验证码校验
        Assert.isTrue(captchaActionService.verificationSmsCaptcha(request.getPhone(), request.getCaptcha()), () -> new ApplicationBizException("验证码已失效!"));
        // 电话号码
        String phone = request.getPhone();
        UserInfo userInfo = userInfoService.existsPhone(phone);
        if (userInfo.getStatus().equals(NumConstant.ONE)){
            throw new ApplicationBizException("该号码已被管理员封禁,无法继续注册");
        }
        if (BoolEnum.isTrue(userInfo.getDeleted())){
            //
        }
        // 密码(明文)
        String cipher = request.getCipher();
        // 盐
        String salt = RandomUtil.randomString(6);
        // 密码(密文)
        String encryptCipher = EncryptUtil.md5Encrypt(cipher, salt);
        // 构建用户实体
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(phone);
        userInfo.setCipher(encryptCipher);
        userInfo.setNickname(this.generateDefaultNickName());

        // 保存

    }

    private String generateDefaultNickName() {
        String s = RandomUtil.randomString(6);
        return StrUtil.builder(DEFAULT_NICKNAME_PREFIX).append(s).toString();
    }
}
