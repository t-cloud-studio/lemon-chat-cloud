package com.example.app.ucenter.domain.request.login;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MobilePhoneCaptchaLoginRequest extends BaseLoginRequest {



    private String captcha;




}
