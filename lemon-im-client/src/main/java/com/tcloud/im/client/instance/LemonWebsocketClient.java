package com.tcloud.im.client.instance;

import com.tcloud.im.client.handlers.LemonWebSocketClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.concurrent.Future;

@Slf4j
public class LemonWebsocketClient {


    private NioEventLoopGroup workerGroup;
    private Bootstrap bootstrap;
    private SslContext sslCtx;

    public LemonWebsocketClient(SslContext sslCtx) {
        this.sslCtx = sslCtx;
        bootstrap = initBootstrap();
    }

    public LemonWebsocketClient() {
        bootstrap = initBootstrap();
    }

    public Bootstrap initBootstrap() {
        // 1个NioEventLoop专门接收请求
        workerGroup = new NioEventLoopGroup(1);
        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true);
        return bootstrap;
    }

    public NioSocketChannel connect(String host, Integer port, OnSuccessFuture onSuccessFuture) {
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) {
                ChannelPipeline pipeline = channel.pipeline();
                if (sslCtx != null) {
                    pipeline.addLast(sslCtx.newHandler(channel.alloc(), host, port));
                }
                pipeline.addLast(new HttpClientCodec(),
                        // HTTP 对象聚合
                        new HttpObjectAggregator(65536),
                        // Ws压缩处理
                        WebSocketClientCompressionHandler.INSTANCE,
                        // 空闲状态程序处理
                        new IdleStateHandler(60, 0, 0),
                        // WebSocket Handler
                        new LemonWebSocketClientHandler(generateUri(host, port)));
            }
        });

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

    private URI generateUri(String host, Integer port) {
        String url;
        if (sslCtx == null) {
            if (port < 1) {
                port = 80;
            }
            url = "ws://" + host + ":" + port;
        } else {
            if (port < 1) {
                port = 443;
            }
            url = "wss://" + host + ":" + port;
        }
        return URI.create(url);
    }

    public void destroy() {
        if (workerGroup != null && !workerGroup.isShutdown()) {
            workerGroup.shutdownGracefully();
        }
    }
}
