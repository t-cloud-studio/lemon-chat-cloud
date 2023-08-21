package com.tcloud.register.manager.client;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.tcloud.redis.config.RedisClient;
import com.tcloud.register.domain.ClientRouteServerInfo;
import com.tcloud.register.domain.ServerInfo;
import com.tcloud.zkclient.cli.ZkClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.tcloud.im.common.constants.ImRedisKeyPatternConstant.CLIENT_SESSION_REGISTER_CENTER_KEY;

/**
 * 客户端会话管理器
 *
 * @author Anker
 */
@Component
@RequiredArgsConstructor
public class ClientRegisterRelateManager {


    private final ZkClient zkClient;
    private final RedisClient redisClient;


    /**
     * 获取当前服务所有客户端会话信息
     *
     * @return {@link Set < Server >} 服务集合
     */
    public List<ClientRouteServerInfo> getAllSessions(String serverId) {
        if (CharSequenceUtil.isBlankOrUndefined(serverId)){
            throw new NullPointerException("server id must be not null!!");
        }
        String zNode = zkClient.getRootPath().concat(StrPool.SLASH).concat(serverId);
        Set<String> sessions = zkClient.getChildrenData(zNode);
        if (CollUtil.isEmpty(sessions)){
            return null;
        }
        List<ClientRouteServerInfo> sessionList = Lists.newArrayList();
        for (String serverJson : sessions) {
            ClientRouteServerInfo session = JSON.parseObject(serverJson, ClientRouteServerInfo.class);
            sessionList.add(session);
        }
        return sessionList;
    }


    /**
     * 从Zk拉取所有服务信息
     *
     * @return {@link Set< ServerInfo >} 服务集合
     */
    public ClientRouteServerInfo find(Long userId) {
        Object serverId = redisClient.hGet(CLIENT_SESSION_REGISTER_CENTER_KEY, userId.toString());
        String sessionNode = StrUtil.builder(zkClient.getRootPath()).append(StrPool.SLASH).append(serverId).append(StrPool.SLASH).append(serverId).toString();
        if (!zkClient.nodeExists(sessionNode)) {
            return null;
        }
        return zkClient.getData(sessionNode, ClientRouteServerInfo.class);
    }


    public String findSessionServerAddress(String userId) {
        Object clientUserRouteInfo = redisClient.hGet(CLIENT_SESSION_REGISTER_CENTER_KEY, userId);
        if (Objects.isNull(clientUserRouteInfo)){
            throw new NullPointerException("not route info for this user");
        }
        ClientRouteServerInfo routeServerInfo = (ClientRouteServerInfo) clientUserRouteInfo;
        return StrUtil.builder(routeServerInfo.getIp()).append(StrPool.COLON).append(routeServerInfo.getPort()).toString();
    }
}
