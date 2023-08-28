package com.tcloud.im.gateway.websocket.handlers;

import cn.hutool.extra.spring.SpringUtil;
import com.tcloud.im.common.enums.Command;
import com.tcloud.im.protocol.msg.WsMessage;
import com.tcloud.im.gateway.websocket.utils.WsMsgUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * @author evans
 * @description
 * @date 2023/8/23
 */
public class ChatFunctionHandlerSelector extends SimpleChannelInboundHandler<WebSocketFrame> {





    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        // 将消息转换成前后端约定的消息类型
        WsMessage wsMessage = WsMsgUtil.covertTextJsonFrameToWsMsg(msg);
        Command cmd = Command.load(wsMessage.getCmd());
        SpringUtil.getBean(CmdHandlerProgress.class).execute(cmd, wsMessage, ctx);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

}
