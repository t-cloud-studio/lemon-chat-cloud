package com.tcloud.im.gateway.websocket.handlers.impl;

import com.tcloud.common.obj.msg.WsMessage;
import com.tcloud.im.common.annotations.CmdHandler;
import com.tcloud.im.common.enums.Command;
import com.tcloud.im.common.utils.CtxHelper;
import com.tcloud.im.gateway.websocket.cache.SocketSessionCache;
import com.tcloud.im.gateway.websocket.handlers.IChatCmdHandler;
import com.tcloud.register.manager.client.ClientRegisterRelateManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author evans
 * @description
 * @date 2023/8/3
 */
@Component
@CmdHandler(cmd = Command.SINGLE_CHAT)
public class SingleChatCmdHandler implements IChatCmdHandler {


    @Autowired
    private ClientRegisterRelateManager registerRelateManager;

    @Override
    public void execute(WsMessage message, NioSocketChannel channel) {
        Long toUser = message.getToUser();
        // 从本服务器找
        if (!SocketSessionCache.exists(toUser)) {
            return;
        }
        // 如果本地存在会话,则执行单条消息发送
        ChannelHandlerContext toUserChannel = SocketSessionCache.get(toUser);
        CtxHelper.writeSuccess(toUserChannel, message);
    }
}
