package com.tcloud.im.client.managers.impl;

import com.tcloud.im.client.instance.LemonClient;
import com.tcloud.im.client.managers.AbstractClientManager;
import com.tcloud.im.lsb.balance.ServerLoadBalance;
import com.tcloud.register.domain.core.Server;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class LemonClientManager extends AbstractClientManager {


    private final LemonClient lemonClient;

    private final ServerLoadBalance serverLoadBalance;

    @Override
    public void connect() {
        // 负载
        Server serverNode = serverLoadBalance.balance();
        channel = lemonClient.connect(serverNode.getIp(), serverNode.getPort(), this::connectFuture);
    }
}
