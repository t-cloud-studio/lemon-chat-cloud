package com.tcloud.web.msg.domain.req;

import com.tcloud.common.obj.enums.MsgType;
import lombok.Data;

@Data
public class GroupMsgSendRequest {


    private Long groupId;

    private MsgType msgType;

    private String content;

}
