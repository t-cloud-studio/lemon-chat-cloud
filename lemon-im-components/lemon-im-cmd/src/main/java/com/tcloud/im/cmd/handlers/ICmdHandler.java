package com.tcloud.im.cmd.handlers;

import com.tcloud.im.protocol.msg.LemonMessage;

/**
 * 命令处理器
 *
 * @author evans
 * @description
 * @date 2023/8/3
 */
public interface ICmdHandler {

    /**
     *
     * @param message
     */
    void receiveMsg(LemonMessage message);

    /**
     *
     * @param message
     */
    void sendMsg(LemonMessage message);


}
