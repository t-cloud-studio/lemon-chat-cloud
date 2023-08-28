package com.tcloud.im.gateway.websocket.handlers;

import com.google.common.collect.Maps;
import com.tcloud.im.common.utils.CtxHelper;
import com.tcloud.im.gateway.websocket.cache.ServerInstanceInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.websocketx.*;

import java.net.URI;

public class WebSocketEventHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ServerInstanceInfo instance = ServerInstanceInfo.instance;

    private final URI uri = URI.create(this.buildUri());

    private String buildUri() {
        return "ws://" + instance.getHost() + ":" + instance.getPort() + "/" + instance.getWsPath();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        DefaultHttpHeaders headers = new DefaultHttpHeaders();
        headers.add(HttpHeaderNames.HOST, uri.getHost());
        WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(
                uri, WebSocketVersion.V13, null, true, headers, 209715200);
        handshaker.handshake(ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        if (msg.text().equals("PING")){
            CtxHelper.writeText(ctx, "PONG");
            return;
        }
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 用户断开连接, 移除本地缓存
//        UserInfoVO attr = (UserInfoVO) CtxHelper.getAttr(ctx, USER_INFO_KEY);
//        SocketSessionCache.removeSession(attr.getId());
        CtxHelper.writeSuccess(ctx, "connection closed!");
        CtxHelper.close(ctx);
    }
}
