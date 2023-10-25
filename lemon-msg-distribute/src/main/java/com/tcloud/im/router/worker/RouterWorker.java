package com.tcloud.im.router.worker;

import com.tcloud.common.obj.msg.WsMessage;
import com.tcloud.im.protocol.msg.LemonMessage;

/**
 * 路由
 */
public abstract class RouterWorker {


    public abstract boolean route(Long userId, LemonMessage message);


    public abstract boolean msgForward(WsMessage message);


    public abstract boolean groupMsgForward(WsMessage message);
}
