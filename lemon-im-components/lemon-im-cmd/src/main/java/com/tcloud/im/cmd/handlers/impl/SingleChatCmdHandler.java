package com.tcloud.im.cmd.handlers.impl;

import com.tcloud.im.cmd.annotations.CmdHandler;
import com.tcloud.im.cmd.handlers.ICmdHandler;
import com.tcloud.im.common.enums.Command;
import com.tcloud.im.protocol.msg.LemonMessage;

/**
 * @author evans
 * @description
 * @date 2023/8/3
 */
@CmdHandler(cmd = Command.SINGLE_CHAT)
public class SingleChatCmdHandler implements ICmdHandler {

    @Override
    public void receiveMsg(LemonMessage message) {

    }

    @Override
    public void sendMsg(LemonMessage message) {

    }
}
