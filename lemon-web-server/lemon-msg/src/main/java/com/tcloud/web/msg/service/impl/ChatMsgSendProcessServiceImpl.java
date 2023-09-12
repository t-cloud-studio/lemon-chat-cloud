package com.tcloud.web.msg.service.impl;

import com.tcloud.web.msg.domain.req.GroupMsgSendRequest;
import com.tcloud.web.msg.domain.req.SingleMsgSendRequest;
import com.tcloud.web.msg.service.ChatMsgSendProcessService;
import org.springframework.stereotype.Service;

@Service
public class ChatMsgSendProcessServiceImpl implements ChatMsgSendProcessService {


    @Override
    public String sendSingleMsg(SingleMsgSendRequest request) {
        return "msg";
    }

    @Override
    public String sendGroupMsg(GroupMsgSendRequest request) {
        return "msg";
    }
}
