package com.tcloud.im.common.utils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CtxHelper {



    public void writeText(ChannelHandlerContext ctx, String content){
        TextWebSocketFrame responseFrame = new TextWebSocketFrame(content);
        ctx.channel().writeAndFlush(responseFrame);
    }


    public void close(ChannelHandlerContext ctx) {
        CloseWebSocketFrame closeWebSocketFrame = new CloseWebSocketFrame(WebSocketCloseStatus.NORMAL_CLOSURE);
        ctx.channel().writeAndFlush(closeWebSocketFrame);
        // 关闭通道
        ctx.close();
    }

    public void close(ChannelHandlerContext ctx, String reason) {
        CloseWebSocketFrame closeWebSocketFrame = new CloseWebSocketFrame(WebSocketCloseStatus.NORMAL_CLOSURE, reason);
        ctx.channel().writeAndFlush(closeWebSocketFrame);
        // 关闭通道
        ctx.close();
    }
}
