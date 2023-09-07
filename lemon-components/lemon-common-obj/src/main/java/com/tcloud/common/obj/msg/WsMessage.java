package com.tcloud.common.obj.msg;


import com.tcloud.common.obj.enums.MsgType;
import lombok.Data;

import java.util.Date;

/**
 * @author evans
 * @description
 * @date 2023/8/23
 */
@Data
public class WsMessage {

    /**
     * 命令
     */
    private byte cmd;
    /**
     * 消息类型
     */
    private MsgType msgType;
    /**
     * 群ID
     */
    private Long groupId;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 发送人
     */
    private Long fromUser;
    /**
     *
     */
    private Long toUser;

    /**
     * 发送时间
     */
    private Date sendTime;
}
