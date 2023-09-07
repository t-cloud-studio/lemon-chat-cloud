package com.tcloud.common.obj.enums;

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
    TXT(1),
    /**
     * 图片消息
     */
    IMG(2),
    /**
     * 音频消息
     */
    AUDIO(3),
    /**
     * 视频消息
     */
    VIDEO(4),
    /**
     * 地图
     */
    LOCATION(5),
    /**
     * 卡片消息
     */
    CARD(6)
    ;


    private final Integer type;



}
