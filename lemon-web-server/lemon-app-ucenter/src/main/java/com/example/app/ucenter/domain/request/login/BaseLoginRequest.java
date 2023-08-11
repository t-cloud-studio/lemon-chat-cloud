package com.example.app.ucenter.domain.request.login;

import lombok.Data;

@Data
public class BaseLoginRequest {


    /**
     * 登录方式
     */
    private Integer loginType;

    /**
     * 登录系统类型
     */
    private Integer loginSysType;
    /**
     * 账号
     */
    private String IdentityKey;


}
