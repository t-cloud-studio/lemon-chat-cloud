package com.tcloud.im.cmd.handlers;

import com.tcloud.im.protocol.message.ProtoMessage;

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
    void receiveMsg(ProtoMessage message);

    /**
     *
     * @param message
     */
    void sendMsg(ProtoMessage message);


}
