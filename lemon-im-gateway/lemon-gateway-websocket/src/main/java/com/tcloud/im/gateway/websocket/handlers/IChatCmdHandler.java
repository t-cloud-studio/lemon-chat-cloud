package com.tcloud.im.gateway.websocket.handlers;

import com.tcloud.common.obj.msg.WsMessage;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author evans
 * @description
 * @date 2023/8/23
 */
public interface IChatCmdHandler {


    /**
     * 执行逻辑
     *
     * @param message
     * @param channel
     */
    void execute(WsMessage message, NioSocketChannel channel);


}
