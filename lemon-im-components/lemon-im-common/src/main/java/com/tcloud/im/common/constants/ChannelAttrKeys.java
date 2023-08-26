package com.tcloud.im.common.constants;

import io.netty.util.AttributeKey;

import java.util.Map;

/**
 * channel 属性绑定key集合
 *
 * @author Anker
 */
public interface ChannelAttrKeys {
    /**
     * Ws 连接路径参数
     */
    AttributeKey<Map<String, String>> PATH_PARAMETERS_KEY = AttributeKey.valueOf("parameters");

    AttributeKey<Object> USER_INFO_KEY = AttributeKey.valueOf("user_info");

}
