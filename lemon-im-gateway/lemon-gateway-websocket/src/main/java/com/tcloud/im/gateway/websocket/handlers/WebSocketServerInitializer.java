package com.tcloud.im.gateway.websocket.handlers;

import com.tcloud.register.manager.client.ClientRegisterRelateManager;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.cors.CorsConfigBuilder;
import io.netty.handler.codec.http.cors.CorsHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 服务初始化器
 *
 * @author evans
 */
public class WebSocketServerInitializer extends ChannelInitializer<NioSocketChannel> {

    private final String webSocketPath;

    private final ClientRegisterRelateManager clientRegisterRelateManager;

    public WebSocketServerInitializer(String webSocketPath,ClientRegisterRelateManager clientRegisterRelateManager) {
        this.webSocketPath = webSocketPath;
        this.clientRegisterRelateManager = clientRegisterRelateManager;
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
                // 路径参数解析处理器,之所以在协议前,是因为携带的参数变更了连接地址
                .addLast(new WsPathParamResolveHandler(webSocketPath))
                // WebSocket 协议处理
                .addLast(new WebSocketServerProtocolHandler(webSocketPath))
                // 解决跨域问题
//                .addLast(new CorsHandler(CorsConfigBuilder.forAnyOrigin().allowNullOrigin().allowCredentials().build()))
                // 认证
                .addLast(new AuthenticationHandler(clientRegisterRelateManager))
                .addLast(new WebSocketEventHandler())
                //  空闲状态程序处理
                .addLast(new IdleStateHandler(60, 0, 0))
                // 其它对话函数处理器
                .addLast(new ChatFunctionHandlerSelector())
                // Exception 捕获处理器
                .addLast(new ExceptionCatchHandler());
    }
}
