package com.tcloud.web.auth.controller;

import com.tcloud.web.auth.domain.request.SendSmsCaptchaRequest;
import com.tcloud.web.auth.domain.vo.GraphCaptchaResp;
import com.tcloud.web.auth.service.CaptchaActionService;
import com.tcloud.web.common.r.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/web/auth/captcha")
public class CaptchaActionController {


    @Autowired
    private CaptchaActionService captchaActionService;


    @GetMapping("/chaff_line_captcha")
    public R<GraphCaptchaResp> getChaffLineCaptcha(){
        return R.data(captchaActionService.getChaffLineCaptcha());
    }


    @PostMapping("/send_sms_captcha")
    public R<Void> sendSmsCaptcha(@RequestBody SendSmsCaptchaRequest request){
        captchaActionService.sendSmsCaptcha(request);
        return R.success();
    }


}
