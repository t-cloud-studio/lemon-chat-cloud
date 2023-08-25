package com.tcloud.im.gateway.websocket.handlers;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class WebSocketServerInitializer extends ChannelInitializer<NioSocketChannel> {

    private final String webSocketPath;

    public WebSocketServerInitializer(String webSocketPath) {
        this.webSocketPath = webSocketPath;
    }

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ch.pipeline()
                //  HTTP解码
                .addLast(new HttpServerCodec())
                // Http对象聚合
                .addLast(new HttpObjectAggregator(1024 * 60))
                // WebSocket 消息压缩
                .addLast(WebSocketClientCompressionHandler.INSTANCE)
                // WebSocket 协议处理
                .addLast(new WebSocketServerProtocolHandler(webSocketPath))
                // 路径参数解析处理器
                .addLast(new WsPathParamResolveHandler())
                // 认证
                .addLast(new AuthenticationHandler())
                //  空闲状态程序处理
                .addLast(new IdleStateHandler(60, 0, 0))
                // 其它对话函数处理器
                .addLast(new ChatFunctionHandlerSelector())
                .addLast(new ExceptionCatchHandler());
    }
}
