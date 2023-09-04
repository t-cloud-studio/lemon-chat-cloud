package com.tcloud.web.auth.service;

import com.tcloud.web.auth.domain.request.SendSmsCaptchaRequest;
import com.tcloud.web.auth.domain.vo.GraphCaptchaResp;

public interface CaptchaActionService {

    /**
     * 获取图形验证码
     *
     * @return {@link GraphCaptchaResp}
     */
    GraphCaptchaResp getChaffLineCaptcha();

    /**
     * 校验验证码是否正确
     *
     * @param requestId 请求ID
     * @param code      请求CODE
     * @return true/ false
     */
    boolean verificationGraphCaptcha(String requestId, String code);

    /**
     * 发送短信验证码
     *
     * @param request
     */
    void sendSmsCaptcha(SendSmsCaptchaRequest request);

    /**
     * 校验验证码是否正确
     *
     * @param phone  电话号码
     * @param captcha   验证码
     * @return true/ false
     */
    boolean verificationSmsCaptcha(String phone, String captcha);
}
