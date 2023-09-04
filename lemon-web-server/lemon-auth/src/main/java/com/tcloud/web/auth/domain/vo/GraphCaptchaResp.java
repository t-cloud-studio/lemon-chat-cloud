package com.tcloud.web.auth.domain.vo;

import lombok.Data;

@Data
public class GraphCaptchaResp {


    private String requestId;


    private String graphCaptcha;


    public GraphCaptchaResp(String requestId, String graphCaptcha) {
        this.requestId = requestId;
        this.graphCaptcha = graphCaptcha;
    }
}
