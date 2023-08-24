package com.tcloud.im.client.handlers;

import com.tcloud.im.common.annotations.CmdHandler;
import com.tcloud.im.common.enums.Command;
import com.tcloud.im.common.exceptions.UnKnowCmdException;
import com.tcloud.im.protocol.msg.LemonMessage;
import io.netty.channel.ChannelHandlerContext;
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
    private List<ICmdHandler> cmdHandlers;


    public void executeReceive(Command cmd, LemonMessage message, ChannelHandlerContext ctx) {
        ICmdHandler iCmdHandler = this.findCmdHandler(cmd);
        iCmdHandler.receiveMsg(message, ctx);
    }


    public void executeSend(Command cmd, LemonMessage message){
        ICmdHandler cmdHandler = findCmdHandler(cmd);
        cmdHandler.sendMsg(message);
    }

    /**
     * 查找命令执行器
     *
     * @param cmd
     * @return
     */
    private ICmdHandler findCmdHandler(Command cmd) {
        ICmdHandler iCmdHandler = cmdHandlers.stream().filter(c -> {
            CmdHandler annotation = c.getClass().getAnnotation(CmdHandler.class);
            return annotation.cmd() == cmd;
        }).findFirst().orElse(null);
        if (Objects.isNull(iCmdHandler)){
            throw new UnKnowCmdException("unKnow cmd handler!! cmd:"+ cmd);
        }
        return iCmdHandler;
    }


}
