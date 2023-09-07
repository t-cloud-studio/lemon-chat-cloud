package com.tcloud.im.gateway.websocket.handlers;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.tcloud.common.obj.vo.UserInfoVO;
import com.tcloud.component.token.utils.TokenUtil;
import com.tcloud.im.common.enums.WsRespCode;
import com.tcloud.im.common.utils.CtxHelper;
import com.tcloud.im.gateway.websocket.cache.ServerInstanceInfo;
import com.tcloud.im.gateway.websocket.cache.SocketSessionCache;
import com.tcloud.register.domain.ClientRouteServerInfo;
import com.tcloud.register.manager.client.ClientRegisterRelateManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

import static com.tcloud.im.common.constants.ChannelAttrKeys.PATH_PARAMETERS_KEY;
import static com.tcloud.im.common.constants.ChannelAttrKeys.USER_INFO_KEY;

/**
 * @author evans
 * @description
 * @date 2023/8/23
 */
@Slf4j
@ChannelHandler.Sharable
public class AuthenticationHandler extends SimpleChannelInboundHandler<WebSocketFrame> {


    private final ClientRegisterRelateManager clientRegisterRelateManager;

    public AuthenticationHandler(ClientRegisterRelateManager clientRegisterRelateManager) {
        this.clientRegisterRelateManager = clientRegisterRelateManager;
    }

    private final ServerInstanceInfo instanceInfo = ServerInstanceInfo.instance;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取连接的URL
        Map<String, String> parameters = CtxHelper.getAttr(ctx, PATH_PARAMETERS_KEY);
        if (MapUtil.isEmpty(parameters)){
            CtxHelper.close(ctx,"认证失败");
            return;
        }
        String token = parameters.get(TokenUtil.getTokenName());
        Long userId = TokenUtil.getUserIdByToken(token);
        if (Objects.isNull(userId)){
            // 用户未登录
            CtxHelper.writeFailAndClose(ctx, WsRespCode.UN_AUTHORIZED, "请登录!!");
            return;
        }
        // 查询用户路由信息是否为本机器
        ClientRouteServerInfo routeServerInfo = clientRegisterRelateManager.find(userId);
        if (Objects.isNull(routeServerInfo)){
            CtxHelper.writeFailAndClose(ctx, WsRespCode.UN_AUTHORIZED, "注册表注册失败!!");
            return;
        }
        if (!routeServerInfo.getIp().equals(instanceInfo.getHost())){
            CtxHelper.writeFailAndClose(ctx, WsRespCode.UN_AUTHORIZED, "注册表注册失败!!");
            return;
        }
        // feign 调用查询用户信息
        UserInfoVO userInfoVO = TokenUtil.getLongUserVOById(userId);
        CtxHelper.setAttr(ctx, USER_INFO_KEY, userInfoVO);
        // 缓存channel到本地
        SocketSessionCache.addSession(userId, ctx);
        log.info("user: >> {} is connected on time:{} | session id is :{}", userId, DateUtil.now(), ctx.channel().id());
        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        String token = CtxHelper.getStrPathParam(ctx, StpUtil.getTokenName());
        // 用户是否登录
        if (!TokenUtil.isLogin(token)) {
            // 用户未登录
            CtxHelper.writeFailAndClose(ctx, WsRespCode.BIZ_ERROR, "请登录!!");
        }
        ctx.fireChannelRead(frame);
    }



}
