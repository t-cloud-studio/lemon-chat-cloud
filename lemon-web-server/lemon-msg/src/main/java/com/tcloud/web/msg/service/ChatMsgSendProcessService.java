package com.tcloud.web.msg.service;

import com.tcloud.web.msg.domain.req.GroupMsgSendRequest;
import com.tcloud.web.msg.domain.req.SingleMsgSendRequest;

public interface ChatMsgSendProcessService {
    /**
     * 发送单聊消息
     *
     * @param request   请求参数
     */
    void sendSingleMsg(SingleMsgSendRequest request);

    /**
     * 发送群聊消息
     *
     * @param request
     */
    void sendGroupMsg(GroupMsgSendRequest request);

}
