package com.tcloud.im.gateway.websocket.handlers;

import cn.hutool.core.map.MapUtil;
import com.tcloud.im.common.utils.CtxHelper;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.Map;

import static com.tcloud.im.common.constants.ChannelAttrKeys.PARAMETERS_KEY;

/**
 * @author evans
 * @description
 * @date 2023/8/23
 */
@ChannelHandler.Sharable
public class AuthenticationHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private boolean isLogin(String token) {
        return false;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // 获取连接的URL
        Map<String, String> parameters = ctx.channel().attr(PARAMETERS_KEY).get();
        if (MapUtil.isEmpty(parameters)){
            CtxHelper.close(ctx,"认证失败");
            return;
        }
        String token = parameters.get("token");
        if (!isLogin(token)){
            CtxHelper.close(ctx,"请重新登录");
            return;
        }
        CtxHelper.writeText(ctx, "hello lemon chat!!" + ctx.channel().id());
        if (frame instanceof TextWebSocketFrame textWebSocketFrame){
            System.out.println(textWebSocketFrame.text());
        }
        ctx.fireChannelRead(frame);

        // 创建一个 TextWebSocketFrame 并发送给客户端
        TextWebSocketFrame responseFrame = new TextWebSocketFrame("hello lemon chat!!");
        ctx.channel().writeAndFlush(responseFrame);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
