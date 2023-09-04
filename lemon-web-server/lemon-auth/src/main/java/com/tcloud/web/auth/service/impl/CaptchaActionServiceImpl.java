package com.tcloud.web.auth.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.tcloud.captcha.enums.SmsSendHandlerEnum;
import com.tcloud.captcha.handlers.graphic.GifCaptcha;
import com.tcloud.captcha.handlers.sms.AbstractSendSmsTypeStrategy;
import com.tcloud.redis.config.RedisClient;
import com.tcloud.web.auth.domain.request.SendSmsCaptchaRequest;
import com.tcloud.web.auth.domain.vo.GraphCaptchaResp;
import com.tcloud.web.auth.service.CaptchaActionService;
import com.tcloud.web.common.constants.RedisKeyPatternConstant;
import com.tcloud.web.common.exceptions.ApplicationBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 验证码动作实现
 *
 * @author Anker
 */
@Service
public class CaptchaActionServiceImpl implements CaptchaActionService {

    @Autowired
    private RedisClient redisClient;

    @Override
    public GraphCaptchaResp getChaffLineCaptcha() {
        // 生成动态GIF验证码
        GifCaptcha gifCaptcha = new GifCaptcha();
        // 验证码内容
        String captchaCode = gifCaptcha.text().toLowerCase();
        // 生成唯一标识
        String captchaKey = UUID.fastUUID().toString();
        String captchaBase64 = gifCaptcha.toBase64();
        // 缓存验证码结果
        String key = String.format(RedisKeyPatternConstant.USER_GRAPH_CAPTCHA_KEY, captchaKey);
        redisClient.setEx(key, captchaCode, 5, TimeUnit.MINUTES);
        // 结果
        return new GraphCaptchaResp(captchaKey, captchaBase64);
    }

    @Override
    public boolean verificationGraphCaptcha(String requestId, String code) {
        String key = String.format(RedisKeyPatternConstant.USER_GRAPH_CAPTCHA_KEY, requestId);
        String captchaCode = redisClient.get(key);
        if (CharSequenceUtil.isBlank(captchaCode)){
            return false;
        }
        return captchaCode.equals(code);
    }


    @Override
    public void sendSmsCaptcha(SendSmsCaptchaRequest request) {
        // 校验图形验证码
        Assert.isTrue(this.verificationGraphCaptcha(request.getRequestId(), request.getPreCaptcha()), () -> new ApplicationBizException("验证码已失效"));
        // 生成6位数验证码
        String captcha = RandomUtil.randomNumbers(6);
        boolean send = SpringUtil.getBean(AbstractSendSmsTypeStrategy.findHandler(SmsSendHandlerEnum.TENCENT)).sendCaptcha(request.getPhone(), captcha);
        if (!send){
            throw new ApplicationBizException("短信发送失败");
        }
        // 短信发送成功, 缓存短信发送验证对
        String key = String.format(RedisKeyPatternConstant.USER_SEND_SMS_CAPTCHA_KEY, request.getPhone());
        redisClient.setEx(key, captcha, 5, TimeUnit.MINUTES);
    }

    @Override
    public boolean verificationSmsCaptcha(String phone, String captcha) {
        String key = String.format(RedisKeyPatternConstant.USER_SEND_SMS_CAPTCHA_KEY, phone);
        String cacheCaptcha = redisClient.get(key);
        if (CharSequenceUtil.isBlank(cacheCaptcha)){
            return false;
        }
        return cacheCaptcha.equals(captcha);
    }
}
