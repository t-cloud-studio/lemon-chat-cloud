package com.tcloud.captcha.handlers.sms.impl;


import com.tcloud.captcha.conf.TencentSmsProperties;
import com.tcloud.captcha.handlers.sms.AbstractSendSmsTypeStrategy;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author evans
 * @description
 * @date 2023/5/28
 */
@Slf4j
@Service
public class TencentSmsSendHandlerImpl extends AbstractSendSmsTypeStrategy {


    @Autowired
    private SmsClient smsClient;
    @Autowired
    private TencentSmsProperties smsProperties;


    @Override
    public boolean sendCaptcha(String phone, String captcha) {
        try {
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
            req.setSmsSdkAppId(smsProperties.getSdkAppId());
            req.setPhoneNumberSet(new String[]{phone});
            req.setSignName(smsProperties.getSign());
            req.setTemplateId(smsProperties.getCaptchaTid());
            req.setTemplateParamSet(new String[]{captcha});
            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = smsClient.SendSms(req);
            // 输出json格式的字符串回包
            log.error("腾讯云短信验证码发送结果:{}", resp);
        } catch (TencentCloudSDKException e) {
            log.error("腾讯云短信发送验证码异常:", e);
            return false;
        }
        return true;
    }


}
