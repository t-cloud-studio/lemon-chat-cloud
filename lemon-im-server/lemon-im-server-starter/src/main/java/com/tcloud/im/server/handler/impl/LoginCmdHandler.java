package com.tcloud.im.server.handler.impl;

import com.tcloud.im.common.annotations.CmdHandler;
import com.tcloud.im.common.enums.Command;
import com.tcloud.im.protocol.msg.LemonMessage;
import com.tcloud.im.server.cache.ServerInstanceInfo;
import com.tcloud.im.server.handler.ICmdHandler;
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

    @Autowired
    private ClientRelationMapRegister clientRelationMapRegister;

    @Override
    public void receiveMsg(LemonMessage message, ChannelHandlerContext ctx) {
        // 执行登录
        Long userId = 0L;
        ServerInstanceInfo instanceInfo = ServerInstanceInfo.instance;
        // 构造用户会话
        ClientRouteServerInfo routeServerInfo = ClientRouteServerInfo.builder().userId(userId).ip(instanceInfo.getHost()).port(instanceInfo.getPort()).build();
        // 注册到用户统一服务器关系映射注册表
        clientRelationMapRegister.register(instanceInfo.getServerId().toString(), routeServerInfo);
        // 并在本地注册会话
        instanceInfo.addSession(userId, (NioSocketChannel) ctx.channel());
    }

    @Override
    public void sendMsg(LemonMessage message) {

    }
}
