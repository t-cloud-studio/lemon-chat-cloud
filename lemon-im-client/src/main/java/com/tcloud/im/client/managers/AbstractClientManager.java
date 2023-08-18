package com.tcloud.im.client.managers;


import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


/**
 * @author Anker
 */
@Slf4j
public abstract class AbstractClientManager {

    protected String serverHost;

    protected Integer serverPort;
    protected Integer retryTimes;
    protected NioSocketChannel channel;
    protected NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);


    public abstract void connect(String host, Integer port);


    public void connectFuture(Future<?> future){
        if (future.isSuccess()) {
            log.info("连接成功!");
        } else if (retryTimes == 0) {
            log.info("重试次数已用完，放弃连接！");
        } else {
            /*
             * 当网络异常恢复后，大量客户端可能会同时发起TCP重连及进行应用层请求，可能会造成服务端过载、网络带宽耗尽等问题，所以增加随机退让机制
             */
            int delay = 1 + ThreadLocalRandom.current().nextInt(0, 5);
            eventLoopGroup.schedule(() -> {
                connect(serverHost, serverPort);
                retryTimes--;
            }, delay, TimeUnit.SECONDS);
        }
    }


}
