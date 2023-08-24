package com.tcloud.web.auth.domain.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {

    /**
     * 用户名
     */
    @NotNull
    private String userName;

    /**
     * 密码
     */
    private String cipher;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 手机验证码
     */
    private String captcha;

}
