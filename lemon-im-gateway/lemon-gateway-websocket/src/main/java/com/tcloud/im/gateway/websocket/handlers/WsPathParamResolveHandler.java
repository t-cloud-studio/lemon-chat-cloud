package com.tcloud.im.gateway.websocket.handlers;

import com.tcloud.im.common.utils.CtxHelper;
import com.tcloud.im.common.utils.PathParamResolver;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.Map;

import static com.tcloud.im.common.constants.ChannelAttrKeys.PATH_PARAMETERS_KEY;

public class WsPathParamResolveHandler extends ChannelInboundHandlerAdapter {


    private final String webSocketPath;

    public WsPathParamResolveHandler(String webSocketPath) {
        this.webSocketPath = webSocketPath;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest httpRequest) {
            String uri = httpRequest.uri();
            // 解析路径参数
            Map<String, String> pathParam = PathParamResolver.resolve(uri);
            // 设置参数到channel
            CtxHelper.setAttr(ctx, PATH_PARAMETERS_KEY, pathParam);
            // 将uri恢复为正常的
            httpRequest.setUri(webSocketPath);
        }
        super.channelRead(ctx, msg);
    }

}
