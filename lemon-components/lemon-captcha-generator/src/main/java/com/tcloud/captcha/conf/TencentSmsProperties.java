package com.tcloud.captcha.conf;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class TencentSmsProperties{



    private String secretId;


    private String secretKey;


    private String endpoint;


    private String sdkAppId;

    private String sign;


    private String captchaTid;


    private String region = "ap-guangzhou";
}
