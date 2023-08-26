package com.tcloud.im.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author evans
 * @description
 * @date 2023/8/26
 */
@Getter
@AllArgsConstructor
public enum WsRespCode {

    /**
     * 成功
     */
    SUCCESS(200,"操作成功"),
    /**
     * 业务异常
     */
    BIZ_ERROR(500 ,"业务异常"),
    /**
     * 请求未授权
     */
    UN_AUTHORIZED(4100, "身份认证失败,请重新登录"),

    ;

    /**
     * 响应码
     */
    private final Integer code;
    /**
     * 响应消息
     */
    private final String msg;

}
