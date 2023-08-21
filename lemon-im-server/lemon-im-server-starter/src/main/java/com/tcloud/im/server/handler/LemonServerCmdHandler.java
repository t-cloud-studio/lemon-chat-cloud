package com.tcloud.im.server.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import com.tcloud.im.common.enums.Command;
import com.tcloud.im.protocol.msg.LemonMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class LemonServerCmdHandler extends SimpleChannelInboundHandler<LemonMessage> {


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LemonMessage msg) throws Exception {
        Command cmd = Command.load(msg.getCmd());
        if (Objects.isNull(cmd)) {
            log.error("invalid cmd :{} ", JSON.toJSON(msg));
        }
        SpringUtil.getBean(CmdHandlerProgress.class).executeReceive(cmd, msg, ctx);
    }
}
