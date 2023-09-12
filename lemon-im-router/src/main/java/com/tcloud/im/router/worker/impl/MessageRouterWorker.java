package com.tcloud.im.router.worker.impl;

import com.tcloud.common.obj.msg.WsMessage;
import com.tcloud.im.protocol.msg.LemonMessage;
import com.tcloud.im.router.cache.GatewayChannelCacheHolder;
import com.tcloud.im.router.connect.ConnectorPool;
import com.tcloud.im.router.worker.RouterWorker;
import com.tcloud.register.domain.ClientRouteServerInfo;
import com.tcloud.register.manager.client.ClientRegisterRelateManager;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MessageRouterWorker extends RouterWorker {


    @Autowired
    private ClientRegisterRelateManager sessionManager;
    @Autowired
    private ConnectorPool connectorPool;


    @Override
    public boolean route(Long userId, LemonMessage message) {
        ClientRouteServerInfo session = sessionManager.find(userId);
        if (Objects.nonNull(session)){
            throw new NullPointerException("client register info is null!!");
        }
        // TODO TCP消息转发

        return false;
    }

    @Override
    public boolean msgForward(WsMessage message) {
        // 获取目标用户的路由信息
        ClientRouteServerInfo routeServerInfo = sessionManager.find(message.getToUser());
        if (GatewayChannelCacheHolder.exists(routeServerInfo.getServerId())){
            Channel channel = GatewayChannelCacheHolder.gtChannel(routeServerInfo.getServerId());
            channel.writeAndFlush(message);
            return true;
        }
        Channel connect = connectorPool.connect(routeServerInfo.getIp(), routeServerInfo.getPort());
        GatewayChannelCacheHolder.setGtChannel(routeServerInfo.getServerId(), connect);
        connect.writeAndFlush(message);
        // 执行连接
        return true;
    }


    @Override
    public boolean groupMsgForward(WsMessage message) {
        return false;
    }
}
