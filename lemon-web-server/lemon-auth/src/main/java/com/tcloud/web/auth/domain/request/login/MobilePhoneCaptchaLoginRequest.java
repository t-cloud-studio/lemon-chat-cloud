package com.tcloud.web.auth.domain.request.login;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MobilePhoneCaptchaLoginRequest extends BaseLoginRequest {



    private String captcha;




}
