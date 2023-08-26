package com.tcloud.im.gateway.websocket.utils;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.text.CharSequenceUtil;
import lombok.experimental.UtilityClass;

/**
 * @author evans
 * @description
 * @date 2023/8/26
 */
@UtilityClass
public class SaTokenHandler {


    /**
     * 使用token获取登录用户id
     *
     * @param token token字符串
     * @return {@link  Long} userId
     */
    public static Long getUserIdByToken(String token) {
        // token
        if (CharSequenceUtil.isBlank(token)) {
            return null;
        }
        return (Long) StpUtil.getLoginIdByToken(token);
    }


}
