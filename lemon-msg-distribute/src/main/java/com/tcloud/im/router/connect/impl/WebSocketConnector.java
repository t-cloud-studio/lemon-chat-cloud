package com.tcloud.im.router.connect.impl;

import com.tcloud.im.router.connect.ConnectorPool;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

@Component
public class WebSocketConnector implements ConnectorPool {


    @Override
    public Channel connect(String host, Integer port) {
        return null;
    }
}
