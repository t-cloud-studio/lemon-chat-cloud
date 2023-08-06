package com.tcloud.im.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author evans
 * @description
 * @date 2023/8/6
 */
@Getter
@AllArgsConstructor
public enum ActionType {

    /**
     * 客户端请求包
     */
    REQUEST((byte) 1, "客户端请求包"),
    /**
     * 响应包
     */
    ACK((byte) 2, "响应包"),
    /**
     * 服务端通知包
     */
    NOTIFY((byte) 3, "服务端通知包");


    private final byte type;

    private final String describe;


}
