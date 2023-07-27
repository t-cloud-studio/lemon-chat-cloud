package com.tcloud.im.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Commands {

    /**
     * 登录请求
     */
    LOGIN_REQUEST(1),
    /**
     * 登录响应
     */
    LOGIN_RESPONSE(2),
    /**
     * 登出请求
     */
    LOGOUT_REQUEST(3),
    /**
     * 登出响应
     */
    LOGOUT_RESPONSE(4),
    /**
     * 心跳请求
     */
    HEARTBEAT_REQUEST(5),
    /**
     * 心跳响应
     */
    HEARTBEAT_RESPONSE(6),
    /**
     * 对话消息请求
     */
    MESSAGE_REQUEST(7),
    /**
     * 对话消息响应
     */
    MESSAGE_RESPONSE(8),
    /**
     * 消息提醒
     */
    MESSAGE_NOTIFICATION(9);

    /**
     * CMD value
     */
    private final Integer cmd;


}
