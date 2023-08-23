package com.tcloud.im.gateway.websocket.domain.core;

import com.tcloud.im.common.enums.MsgType;
import com.tcloud.im.gateway.websocket.domain.user.FromUser;
import com.tcloud.im.gateway.websocket.domain.user.ToUser;
import lombok.Data;

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
    private FromUser fromUser;
    /**
     *
     */
    private ToUser toUser;
}
