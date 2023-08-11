package com.tcloud.web.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RespCodeEnum {


    SUCCESS(2000,"操作成功"),
    FAILED(9999,"操作失败")
    ;


    private final Integer code;


    private final String msg;


}
