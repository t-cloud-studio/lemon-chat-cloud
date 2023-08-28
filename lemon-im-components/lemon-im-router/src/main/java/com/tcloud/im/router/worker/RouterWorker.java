package com.tcloud.im.router.worker;

import com.tcloud.im.protocol.msg.LemonMessage;
import com.tcloud.im.protocol.msg.WsMessage;

/**
 * 路由
 */
public abstract class RouterWorker {


    public abstract boolean route(Long userId, LemonMessage message);


    public abstract boolean msgForward(WsMessage message);


    public abstract boolean groupMsgForward(WsMessage message);
}
