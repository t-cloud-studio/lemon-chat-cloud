package com.tcloud.im.gateway.websocket.handlers;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.tcloud.feign.api.domain.vo.UserInfoVO;
import com.tcloud.feign.api.modules.user.UserInfoFeignService;
import com.tcloud.im.common.enums.WsRespCode;
import com.tcloud.im.common.utils.CtxHelper;
import com.tcloud.im.gateway.websocket.cache.SocketSessionCache;
import com.tcloud.im.gateway.websocket.utils.SaTokenHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.Map;
import java.util.Objects;

import static com.tcloud.im.common.constants.ChannelAttrKeys.PATH_PARAMETERS_KEY;
import static com.tcloud.im.common.constants.ChannelAttrKeys.USER_INFO_KEY;

/**
 * @author evans
 * @description
 * @date 2023/8/23
 */
@ChannelHandler.Sharable
public class AuthenticationHandler extends SimpleChannelInboundHandler<WebSocketFrame> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // 获取连接的URL
        Map<String, String> parameters = CtxHelper.getAttr(ctx, PATH_PARAMETERS_KEY);
        if (MapUtil.isEmpty(parameters)){
            CtxHelper.close(ctx,"认证失败");
            return;
        }
        String token = parameters.get(StpUtil.getTokenName());
        Long userId = SaTokenHandler.getUserIdByToken(token);
        if (Objects.isNull(userId)){
            // 用户未登录
            CtxHelper.writeFailAndClose(ctx, WsRespCode.BIZ_ERROR, "请登录!!");
        }
        // feign 调用查询用户信息
        UserInfoVO loginUserInfo = SpringUtil.getBean(UserInfoFeignService.class).getLoginUserInfo(userId);
        CtxHelper.setAttr(ctx, USER_INFO_KEY, loginUserInfo);
        // 缓存channel到本地
        SocketSessionCache.addSession(userId, ctx);
        CtxHelper.writeSuccess(ctx, "Authentication Success!!");
        ctx.fireChannelRead(frame);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 用户断开连接, 移除本地缓存
        UserInfoVO attr = (UserInfoVO) CtxHelper.getAttr(ctx, USER_INFO_KEY);
        SocketSessionCache.removeSession(attr.getId());
        CtxHelper.close(ctx);
    }
}
