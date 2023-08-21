package com.tcloud.app.ucenter.domain.request.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BaseLoginRequest {


    /**
     * 登录方式
     */
    private Integer loginType;

    /**
     * 账号
     */
    private String IdentityKey;


}
