package com.tcloud.captcha.handlers.sms;


import com.tcloud.captcha.enums.SmsSendHandlerEnum;
import com.tcloud.captcha.handlers.sms.impl.TencentSmsSendHandlerImpl;

/**
 * @author evans
 * @description
 * @date 2023/5/28
 */
public abstract class AbstractSendSmsTypeStrategy {

    /**
     * 根据注册类型获取逻辑处理策略
     *
     * @param handlerEnum 注册类型
     * @return 策略
     */
    public static Class<? extends AbstractSendSmsTypeStrategy> findHandler(SmsSendHandlerEnum handlerEnum) {
        // 腾讯云
        if (handlerEnum == SmsSendHandlerEnum.TENCENT) {
            return TencentSmsSendHandlerImpl.class;
        }
        throw new NullPointerException("暂未接入该短信平台");
    }

    /**
     * 发送验证码
     *
     * @param phone 电话号码
     * @param captcha 验证码
     * @return 成功与否
     */
    public abstract boolean sendCaptcha(String phone, String captcha);


}
