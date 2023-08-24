package com.tcloud.im.client.handlers.impl;

import com.tcloud.im.client.handlers.ICmdHandler;
import com.tcloud.im.common.annotations.CmdHandler;
import com.tcloud.im.common.enums.Command;
import com.tcloud.im.protocol.msg.LemonMessage;
import com.tcloud.register.domain.ClientRouteServerInfo;
import com.tcloud.register.handler.client.ClientRelationMapRegister;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author evans
 * @description
 * @date 2023/8/3
 */
@CmdHandler(cmd = Command.LOGIN)
public class LoginCmdHandler implements ICmdHandler {


    @Override
    public void receiveMsg(LemonMessage message, ChannelHandlerContext ctx) {

    }

    @Override
    public void sendMsg(LemonMessage message) {

    }
}
