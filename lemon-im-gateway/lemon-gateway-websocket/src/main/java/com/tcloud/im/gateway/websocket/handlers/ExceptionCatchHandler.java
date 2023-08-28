package com.tcloud.im.gateway.websocket.handlers;

import com.tcloud.im.common.utils.CtxHelper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionCatchHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("ctx has exception:", cause);
        CtxHelper.writeFail(ctx, "业务异常");
        super.exceptionCaught(ctx, cause);
    }
}
