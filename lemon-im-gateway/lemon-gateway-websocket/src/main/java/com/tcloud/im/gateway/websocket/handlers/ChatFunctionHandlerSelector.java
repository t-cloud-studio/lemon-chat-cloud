package com.tcloud.im.gateway.websocket.handlers;

import cn.hutool.extra.spring.SpringUtil;
import com.tcloud.im.common.enums.Command;
import com.tcloud.im.gateway.websocket.domain.core.WsMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.springframework.stereotype.Component;

/**
 * @author evans
 * @description
 * @date 2023/8/23
 */
public class ChatFunctionHandlerSelector extends SimpleChannelInboundHandler<WebSocketFrame> {





    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        System.out.println(msg.content().readByte());
//        Command cmd = Command.load();
//        SpringUtil.getBean(CmdHandlerProgress.class).execute(cmd, msg, ctx);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

}
