package com.tcloud.im.common.utils;

import com.alibaba.fastjson2.JSON;
import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;

@UtilityClass
public class JsonUtil {





    public <T> T parseByteToObject(byte[] bytes, Class<T> type){
        String jsonStr = new String(bytes, StandardCharsets.UTF_8);
        return JSON.parseObject(jsonStr, type);
    }


    public static String toJson(Object content) {
        return JSON.toJSONString(content);
    }
}
