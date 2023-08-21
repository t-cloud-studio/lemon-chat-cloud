package com.tcloud.web.common.enums;

import com.tcloud.web.common.r.IResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Anker
 * @date 2023/3/23 12:34
 * 结果状态码
 */
@Getter
@AllArgsConstructor
public enum ResultCode implements IResultCode {

    /**
     * 操作成功
     */
    SUCCESS(2000, "操作成功"),

    /**
     * 业务异常
     */
    FAILURE(9999, "业务异常"),

    /**
     * 请求未授权
     */
    UN_AUTHORIZED(4100, "请求未授权"),

    ;


    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;
}
