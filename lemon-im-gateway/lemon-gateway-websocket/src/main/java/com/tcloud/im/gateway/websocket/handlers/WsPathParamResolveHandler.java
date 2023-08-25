package com.tcloud.im.gateway.websocket.handlers;

import com.tcloud.im.common.utils.PathParamResolver;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.Map;

import static com.tcloud.im.common.constants.ChannelAttrKeys.PARAMETERS_KEY;

public class WsPathParamResolveHandler extends SimpleChannelInboundHandler<WebSocketFrame> {




    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest httpRequest) {
            String uri = httpRequest.uri();
            // 解析路径参数
            Map<String, String> resolve = PathParamResolver.resolve(uri);
            // 设置参数到channel
            ctx.channel().attr(PARAMETERS_KEY).set(resolve);
        }
        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        // 直接放行
        ctx.fireChannelRead(msg);
    }
}
