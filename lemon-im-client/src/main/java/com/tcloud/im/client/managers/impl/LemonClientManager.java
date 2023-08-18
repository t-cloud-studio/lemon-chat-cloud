package com.tcloud.im.client.managers.impl;

import com.tcloud.im.client.instance.LemonClient;
import com.tcloud.im.client.managers.AbstractClientManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LemonClientManager extends AbstractClientManager {


    private final LemonClient lemonClient;

    @Override
    public void connect(String host, Integer port) {
        serverHost = host;
        serverPort = port;
        channel = lemonClient.connect(host, port, this::connectFuture);
    }
}
