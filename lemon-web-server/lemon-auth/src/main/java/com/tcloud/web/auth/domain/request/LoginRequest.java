package com.tcloud.web.auth.domain.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

    /**
     * 登录方式
     */
    @NotNull(message = "请选择登录方式")
    private Integer loginType;
    /**
     * 账号
     */
    private String identityKey;
    /**
     * 密码
     */
    private String cipher;
    /**
     * 验证码
     */
    private String captcha;
    /**
     * 微信开放id
     */
    private String openId;



}
