package com.tcloud.im.common.enums;

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
    VIDEO(4)
    ;


    private final Integer type;



}
