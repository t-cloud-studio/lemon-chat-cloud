package com.tcloud.web.auth.domain.request;

import lombok.Data;

@Data
public class SendSmsCaptchaRequest {
    /**
     * 手机
     */
    private String phone;
    /**
     * 前置验证码
     */
    private String preCaptcha;
    /**
     * 前置验证码请求id
     */
    private String requestId;

}
