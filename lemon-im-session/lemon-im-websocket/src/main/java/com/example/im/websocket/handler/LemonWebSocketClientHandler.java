package com.example.im.websocket.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import com.tcloud.im.cmd.handlers.CmdHandlerProgress;
import com.tcloud.im.common.enums.Command;
import com.tcloud.im.protocol.msg.LemonMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static io.netty.handler.timeout.IdleState.WRITER_IDLE;


/**
 * 客户端处理
 *
 * @author will
 */
@Slf4j
public class LemonWebSocketClientHandler extends SimpleChannelInboundHandler<Object> {


    private WebSocketClientHandshaker handshake;

    private final URI uri;

    public LemonWebSocketClientHandler(URI uri) {
        this.uri = uri;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent idleStateEvent) {
            if (WRITER_IDLE.equals(idleStateEvent.state())) {
                ctx.channel().writeAndFlush(new PingWebSocketFrame());
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        handshake = WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, null, true, new DefaultHttpHeaders());
        handshake.handshake(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {

        Channel ch = channelHandlerContext.channel();
        if (!handshake.isHandshakeComplete()) {
            handshake.finishHandshake(ch, (FullHttpResponse) msg);
            System.out.println("WebSocket Client connected!");
            return;
        }

        if (msg instanceof FullHttpResponse response) {
            throw new IllegalStateException("Unexpected FullHttpResponse (getStatus=" + response.status() + ", content=" + response.content().toString(StandardCharsets.UTF_8) + ')');
        }

        if (msg instanceof TextWebSocketFrame textFrame) {
            LemonMessage lemonMessage = JSON.parseObject(textFrame.text(), LemonMessage.class);
            Command cmd = Command.load(lemonMessage.getCmd());
            if (Objects.isNull(cmd)) {
                log.error("param error: cmd");
                throw new IllegalStateException("param error: cmd");
            }
            SpringUtil.getBean(CmdHandlerProgress.class).executeReceive(cmd, lemonMessage);
        } else if (msg instanceof PongWebSocketFrame) {
            System.out.println("WebSocket Client received pong");
        } else if (msg instanceof CloseWebSocketFrame) {
            System.out.println("WebSocket Client received closing");
            ch.close();
        }
    }
}
