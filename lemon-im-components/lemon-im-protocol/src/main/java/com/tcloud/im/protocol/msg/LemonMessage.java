package com.tcloud.im.protocol.msg;

import lombok.Data;

/**
 * @author evans
 * @description
 * @date 2023/8/6
 */

@Data
public class LemonMessage {


    /**
     * 版本
     */
    private int version;

    /**
     * 业务类型，心跳、推送、单聊、群聊、系统消息、聊天室、客服
     * see cmdType
     */
    private byte cmd;

    /**
     * 消息类型 request response notify
     */
    private byte msgType;

    /**
     * 调试性日志
     */
    private int logId;

    /**
     * 序列化ID，可以用作异步处理
     */
    private int sequenceId;

    /**
     * 消息的内容
     */
    private byte[] data;


}
