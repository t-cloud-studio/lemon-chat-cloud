package com.tcloud.im.client.instance;

import com.tcloud.im.client.handlers.LemonClientHandler;
import com.tcloud.im.protocol.codec.ProtobufDecoder;
import com.tcloud.im.protocol.codec.ProtobufEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author evans
 * @description
 * @date 2023/8/6
 */
@Slf4j
public class LemonClient {

    /**
     * 主机
     */
    private String host;
    /**
     * 端口
     */
    private Integer port;
    private NioEventLoopGroup workerGroup;
    private Bootstrap bootstrap;

    public LemonClient() {
        bootstrap = initBootstrap();
    }

    private Bootstrap initBootstrap() {
        // 1个NioEventLoop专门接收请求，1个NioEventLoop处理ACK
        workerGroup = new NioEventLoopGroup(1);

        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) {
                        channel.pipeline()
                                // 客户端发现 180 秒未从服务端读取到消息，主动断开连接
                                .addLast(new ReadTimeoutHandler(180, TimeUnit.SECONDS))
                                // 服务端每 60 秒向服务端发起一次心跳消息，保证客户端端可以读取到消息。有3次机会保证不断开
                                .addLast(new IdleStateHandler(60, 0, 0))
                                .addLast(new ProtobufDecoder())
                                .addLast(new ProtobufEncoder())
                                .addLast(new LemonClientHandler());
                    }
                });
        return bootstrap;
    }

    /**
     * 客户端重连
     */
    public NioSocketChannel connect(String host, Integer port, OnSuccessFuture onSuccessFuture) {
        try {
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync().addListener(onSuccessFuture::onFuture);
            return (NioSocketChannel) channelFuture.channel();
        } catch (InterruptedException e) {
            log.error("error", e);
            return null;
        }
    }

    @FunctionalInterface
    public interface OnSuccessFuture {
        /**
         * onFuture
         *
         * @param future future
         */
        void onFuture(Future<?> future);
    }

    public void destroy() {
        if (workerGroup != null && !workerGroup.isShutdown()) {
            workerGroup.shutdownGracefully();
        }
    }


}

