package com.tcloud.im.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 命令
 *
 * @author Anker
 */
@Getter
@AllArgsConstructor
public enum Commands {

    /**
     * 登录请求
     */
    LOGIN(1),
    /**
     * 登出请求
     */
    LOGOUT(2),
    /**
     * 心跳请求
     */
    HEARTBEAT(3),
    /**
     * 对话消息请求
     */
    SINGLE_CHAT(4),
    /**
     * 对话消息响应
     */
    GROUP_CHAT(5),
    /**
     * 消息提醒
     */
    NOTIFICATION(6);
    /**
     * CMD value
     */
    private final int cmd;


}
