package com.tcloud.im.gateway.websocket.handlers;

import com.tcloud.im.gateway.websocket.domain.core.WsMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

/**
 * @author evans
 * @description
 * @date 2023/8/23
 */
@Component
public class AuthenticationHandler extends SimpleChannelInboundHandler<WsMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, WsMessage wsMessage) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
