package com.tcloud.app.ucenter.domain.request.login;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CipherPairLoginRequest extends BaseLoginRequest{


    /**
     * 密码
     */
    private String cipher;



}
