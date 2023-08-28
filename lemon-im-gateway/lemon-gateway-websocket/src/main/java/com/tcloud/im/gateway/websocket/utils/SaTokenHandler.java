package com.tcloud.im.gateway.websocket.utils;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.tcloud.im.common.utils.CtxHelper;
import io.netty.channel.ChannelHandlerContext;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Objects;

import static com.tcloud.im.common.constants.ChannelAttrKeys.PATH_PARAMETERS_KEY;

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
        Object loginIdByToken = StpUtil.getLoginIdByToken(token);
        if (Objects.isNull(loginIdByToken)){
            return null;
        }
        return Long.parseLong(loginIdByToken.toString());
    }

    /**
     * 用户是否登录
     *
     * @param ctx channel
     * @return true or false
     */
    public static boolean isLogin(ChannelHandlerContext ctx){
        String token = CtxHelper.getStrPathParam(ctx, StpUtil.getTokenName());
        return Objects.nonNull(getUserIdByToken(token));
    }


}
