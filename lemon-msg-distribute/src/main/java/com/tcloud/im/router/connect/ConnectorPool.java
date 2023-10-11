package com.tcloud.im.router.connect;

import io.netty.channel.Channel;

public interface ConnectorPool {

    public Channel connect(String host, Integer port);

}
