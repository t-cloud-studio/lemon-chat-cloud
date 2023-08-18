package com.tcloud.im.router.worker;

import com.tcloud.im.protocol.msg.LemonMessage;

/**
 * 路由
 */
public abstract class RouterWorker {







    public abstract boolean route(Long userId, LemonMessage message);



}
