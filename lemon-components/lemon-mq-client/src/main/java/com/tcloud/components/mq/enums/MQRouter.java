package com.tcloud.components.mq.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MQRouter {


    SINGLE_MSG(1000, "单聊消息","biz_chat_single_msg", "GID_biz_chat_single_msg", "biz_chat_single_msg"),

    GROUP_MSG(1001, "群聊消息","biz_chat_group_msg", "GID_biz_chat_group_msg", "biz_chat_group_msg"),

    NOTIFICATION_MSG(1002, "用户消息提醒","biz_notification_msg", "GID_biz_notification_msg", "biz_notification_msg"),
    ;

    private final Integer code;

    private final String bizDes;

    private final String topic;

    private final String gid;

    private final String tag;

}
