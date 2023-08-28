package com.tcloud.im.gateway.websocket.handlers;

import com.tcloud.im.common.annotations.CmdHandler;
import com.tcloud.im.common.enums.Command;
import com.tcloud.im.common.exceptions.UnKnowCmdException;
import com.tcloud.im.protocol.msg.WsMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author evans
 * @description
 * @date 2023/8/6
 */
@Component
public class CmdHandlerProgress {


    @Autowired
    private List<IChatCmdHandler> cmdHandlers;


    public void execute(Command cmd, WsMessage message, ChannelHandlerContext ctx) {
        IChatCmdHandler iCmdHandler = this.findCmdHandler(cmd);
        iCmdHandler.execute(message, (NioSocketChannel) ctx.channel());
    }


    /**
     * 查找命令执行器
     *
     * @param cmd
     * @return
     */
    private IChatCmdHandler findCmdHandler(Command cmd) {
        IChatCmdHandler iCmdHandler = cmdHandlers.stream().filter(c -> {
            CmdHandler annotation = c.getClass().getAnnotation(CmdHandler.class);
            return annotation.cmd() == cmd;
        }).findFirst().orElse(null);
        if (Objects.isNull(iCmdHandler)){
            throw new UnKnowCmdException("unKnow cmd handler!! cmd:"+ cmd);
        }
        return iCmdHandler;
    }


}
