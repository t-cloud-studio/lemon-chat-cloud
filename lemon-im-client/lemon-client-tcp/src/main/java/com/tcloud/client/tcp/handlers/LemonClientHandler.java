package com.tcloud.client.tcp.handlers;

import cn.hutool.extra.spring.SpringUtil;
import com.tcloud.im.common.enums.Command;
import com.tcloud.im.common.exceptions.UnKnowCmdException;
import com.tcloud.im.protocol.msg.LemonMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author evans
 * @description
 * @date 2023/8/6
 */
@Slf4j
public class LemonClientHandler extends SimpleChannelInboundHandler<LemonMessage> {


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LemonMessage msg) throws Exception {
        Command cmdType = Command.load(msg.getCmd());
        if(cmdType == null){
            log.error("param error: cmd");
            throw new UnKnowCmdException("param error: cmd");
        }
        SpringUtil.getBean(CmdHandlerProgress.class).executeReceive(cmdType, msg, ctx);
    }
}
