package com.tcloud.im.router.worker.impl;

import com.tcloud.im.protocol.msg.LemonMessage;
import com.tcloud.im.router.worker.RouterWorker;
import com.tcloud.register.domain.core.ClientSession;
import com.tcloud.register.handler.client.ClientSessionManager;
import io.netty.channel.ChannelFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageRouterWorker extends RouterWorker {


    private final ClientSessionManager sessionManager;

    @Override
    public boolean route(Long userId, LemonMessage message) {
        ClientSession session = sessionManager.find(userId);
        ChannelFuture channelFuture = session.getChannel().writeAndFlush(message);
        return false;
    }
}
