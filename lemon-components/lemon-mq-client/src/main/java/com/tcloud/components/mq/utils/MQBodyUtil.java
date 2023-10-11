package com.tcloud.components.mq.utils;

import com.alibaba.fastjson.JSON;

import java.nio.charset.StandardCharsets;

public class MQBodyUtil {
    /**
     * 转换成目标对象
     *
     * @param body      消息Byte
     * @param parseType 转换成指定类型
     * @return {@link T} 转换后的对象
     * @param <T> parseType
     */
    public static <T> T parseToObject(byte[] body, Class<T> parseType) {
        String messageContent = new String(body, StandardCharsets.UTF_8);
        return JSON.parseObject(messageContent, parseType);
    }
}
