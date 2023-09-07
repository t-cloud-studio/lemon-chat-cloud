package com.tcloud.web.msg.domain.req;

import com.tcloud.common.obj.enums.MsgType;
import lombok.Data;

@Data
public class SingleMsgSendRequest {


    private Long toUser;

    private MsgType msgType;

    private String content;


}
