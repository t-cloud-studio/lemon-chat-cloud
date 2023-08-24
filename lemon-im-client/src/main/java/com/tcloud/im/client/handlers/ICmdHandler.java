package com.tcloud.im.client.handlers;

import com.tcloud.im.protocol.msg.LemonMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * 命令处理器
 *
 * @author evans
 * @description
 * @date 2023/8/3
 */
public interface ICmdHandler {

    /**
     * @param message
     * @param ctx
     */
    void receiveMsg(LemonMessage message, ChannelHandlerContext ctx);

    /**
     *
     * @param message
     */
    void sendMsg(LemonMessage message);


}
