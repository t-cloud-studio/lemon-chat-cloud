package com.tcloud.web.msg.controller;


import com.tcloud.web.common.r.R;
import com.tcloud.web.msg.domain.req.GroupMsgSendRequest;
import com.tcloud.web.msg.domain.req.SingleMsgSendRequest;
import com.tcloud.web.msg.service.ChatMsgSendProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/chat_msg/send")
public class ChatMsgController {

    @Autowired
    private ChatMsgSendProcessService chatMsgSendProcessService;

    @PostMapping("single")
    public R<Void> sendMsg(@RequestBody SingleMsgSendRequest request){
        chatMsgSendProcessService.sendSingleMsg(request);
        return R.success();
    }


    @PostMapping("group")
    public R<Void> sendGroupMsg(@RequestBody GroupMsgSendRequest request){
        chatMsgSendProcessService.sendGroupMsg(request);
        return R.success();
    }



}
