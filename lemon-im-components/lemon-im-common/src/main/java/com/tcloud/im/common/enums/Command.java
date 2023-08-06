package com.tcloud.im.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 命令
 *
 * @author Anker
 */
@Getter
@AllArgsConstructor
public enum Command {

    /**
     * 登录请求
     */
    LOGIN((byte)1),
    /**
     * 登出请求
     */
    LOGOUT((byte)2),
    /**
     * 心跳请求
     */
    HEARTBEAT((byte)3),
    /**
     * 对话消息请求
     */
    SINGLE_CHAT((byte)4),
    /**
     * 对话消息响应
     */
    GROUP_CHAT((byte)5),
    /**
     * 消息提醒
     */
    NOTIFICATION((byte)6);
    /**
     * CMD value
     */
    private final byte cmd;


    public static Command load(byte cmd){
        return Arrays.stream(values()).filter(c -> c.getCmd() == cmd).findAny().orElse(null);
    }

}
