package com.tcloud.client.tcp.handlers.impl;

import com.tcloud.client.ws.handlers.ICmdHandler;
import com.tcloud.im.common.annotations.CmdHandler;
import com.tcloud.im.common.enums.Command;
import com.tcloud.im.protocol.msg.LemonMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * 群聊
 *
 * @author evans
 * @description
 * @date 2023/8/3
 */
@CmdHandler(cmd = Command.GROUP_CHAT)
public class GroupChatCmdHandler implements ICmdHandler {


    @Override
    public void receiveMsg(LemonMessage message, ChannelHandlerContext ctx) {

    }

    @Override
    public void sendMsg(LemonMessage message) {

    }
}
