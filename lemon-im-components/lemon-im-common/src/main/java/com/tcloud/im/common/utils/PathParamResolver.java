package com.tcloud.im.common.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class PathParamResolver {


    public Map<String, String> resolve(String uri){
        // ws://x.x.x.x:8000/path?pa=va&pb=vb
        List<String> split = StrUtil.split(uri, "?");
        if (split.size() == 1){
            return null;
        }
        // 取索引位置1的值
        String paramStr = split.get(1);
        if (StrUtil.isBlank(paramStr)){
            return null;
        }
        List<String> paramPairs = StrUtil.split(paramStr, "&");
        Map<String, String> paramMap = new HashMap<>(paramPairs.size());
        for (String p : paramPairs) {
            List<String> pair = StrUtil.split(p, "=");
            if (pair.size() == 2) {
                paramMap.put(pair.get(0), pair.get(1));
            }
        }
        return paramMap;
    }


}
