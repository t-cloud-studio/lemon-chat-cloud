package com.tcloud.web.auth.domain.request;

import lombok.Data;

@Data
public class RegisterRequest {


    /**
     * 手机号码
     */
    private String phone;


    /**
     * 密码
     */
    private String cipher;


    /**
     * 手机验证码
     */
    private String captcha;

    /**
     * 前置验证码
     */
    private String preCaptcha;
    /**
     * 前置验证码请求id
     */
    private String requestId;
}
