package com.tcloud.im.gateway.websocket.handlers.impl;

import com.tcloud.common.obj.msg.WsMessage;
import com.tcloud.im.common.annotations.CmdHandler;
import com.tcloud.im.common.enums.Command;
import com.tcloud.im.gateway.websocket.handlers.IChatCmdHandler;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 群聊
 *
 * @author evans
 * @description
 * @date 2023/8/3
 */
@CmdHandler(cmd = Command.GROUP_CHAT)
public class GroupChatCmdHandler implements IChatCmdHandler {

    @Override
    public void execute(WsMessage message, NioSocketChannel channel) {

    }
}
