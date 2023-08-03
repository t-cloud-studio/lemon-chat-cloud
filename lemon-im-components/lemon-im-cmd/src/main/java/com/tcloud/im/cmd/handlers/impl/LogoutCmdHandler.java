package com.tcloud.im.cmd.handlers.impl;

import com.tcloud.im.cmd.annotations.CmdHandler;
import com.tcloud.im.cmd.handlers.ICmdHandler;
import com.tcloud.im.common.enums.Commands;
import com.tcloud.im.protocol.message.ProtoMessage;

/**
 * @author evans
 * @description
 * @date 2023/8/3
 */
@CmdHandler(cmd = Commands.LOGOUT)
public class LogoutCmdHandler implements ICmdHandler {

    @Override
    public void receiveMsg(ProtoMessage message) {

    }

    @Override
    public void sendMsg(ProtoMessage message) {

    }
}
