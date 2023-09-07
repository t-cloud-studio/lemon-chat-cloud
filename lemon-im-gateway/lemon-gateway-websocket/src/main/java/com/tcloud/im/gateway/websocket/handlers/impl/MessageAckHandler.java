package com.tcloud.im.gateway.websocket.handlers.impl;

import com.tcloud.common.obj.msg.WsMessage;
import com.tcloud.im.gateway.websocket.handlers.IChatCmdHandler;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 消息签收处理器
 *
 * @author evans
 * @description
 * @date 2023/8/26
 */
public class MessageAckHandler implements IChatCmdHandler {


    @Override
    public void execute(WsMessage message, NioSocketChannel channel) {

    }
}
