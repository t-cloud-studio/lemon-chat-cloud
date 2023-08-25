package com.tcloud.im.common.constants;

import io.netty.util.AttributeKey;

import java.util.Map;

public interface ChannelAttrKeys {

    AttributeKey<Map<String, String>> PARAMETERS_KEY = AttributeKey.valueOf("parameters");

}
