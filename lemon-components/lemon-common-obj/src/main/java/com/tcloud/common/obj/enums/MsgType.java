package com.tcloud.common.obj.enums;

import com.tcloud.common.obj.msg.CardMsgContent;
import com.tcloud.common.obj.msg.LocationMsgContent;
import com.tcloud.common.obj.msg.MediaMsgContent;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author evans
 * @description
 * @date 2023/8/6
 */
@Getter
@AllArgsConstructor
public enum MsgType {

    /**
     * 文本消息
     */
    TXT(1, String.class),
    /**
     * 图片消息
     */
    IMG(2, MediaMsgContent.class),
    /**
     * 音频消息
     */
    AUDIO(3, MediaMsgContent.class),
    /**
     * 视频消息
     */
    VIDEO(4, MediaMsgContent.class),
    /**
     * 地图
     */
    LOCATION(5, LocationMsgContent.class),
    /**
     * 卡片消息
     */
    CARD(6, CardMsgContent.class)
    ;


    private final Integer type;

    /**
     * msgMode 类型
     */
    private final Class<?> msgMode;


}
