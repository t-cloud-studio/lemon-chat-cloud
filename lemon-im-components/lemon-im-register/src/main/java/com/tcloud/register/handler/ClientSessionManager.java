package com.tcloud.register.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tcloud.im.common.constants.ImRedisKeyPatternConstant;
import com.tcloud.redis.config.RedisClient;
import com.tcloud.register.domain.core.ClientSession;
import com.tcloud.register.domain.core.Server;
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
public class ClientSessionManager {


    private final ZkClient zkClient;
    private final RedisClient redisClient;


    /**
     * 获取当前服务所有客户端会话信息
     *
     * @return {@link Set < Server >} 服务集合
     */
    public List<ClientSession> getAllSessions(String serverId) {
        if (CharSequenceUtil.isBlankOrUndefined(serverId)){
            throw new NullPointerException("server id must be not null!!");
        }
        String zNode = zkClient.getRootPath().concat(StrPool.SLASH).concat(serverId);
        Set<String> sessions = zkClient.getChildrenData(zNode);
        if (CollUtil.isEmpty(sessions)){
            return null;
        }
        List<ClientSession> sessionList = Lists.newArrayList();
        for (String serverJson : sessions) {
            ClientSession session = JSON.parseObject(serverJson, ClientSession.class);
            sessionList.add(session);
        }
        return sessionList;
    }


    /**
     * 从Zk拉取所有服务信息
     *
     * @return {@link Set<Server>} 服务集合
     */
    public ClientSession find(String sessionId) {
        Object serverId = redisClient.hGet(CLIENT_SESSION_REGISTER_CENTER_KEY, sessionId);
        String sessionNode = StrUtil.builder(zkClient.getRootPath()).append(StrPool.SLASH).append(serverId).append(StrPool.SLASH).append(serverId).toString();
        if (!zkClient.nodeExists(sessionNode)) {
            return null;
        }
        return zkClient.getData(sessionNode, ClientSession.class);
    }


    public String findSessionServerAddress(String sessionId) {
        Object serverId = redisClient.hGet(CLIENT_SESSION_REGISTER_CENTER_KEY, sessionId);
        String serverNode = StrUtil.builder(zkClient.getRootPath()).append(StrPool.SLASH).append(serverId).toString();
        String sessionNode = StrUtil.builder(serverNode).append(StrPool.SLASH).append(serverId).toString();

        if (!zkClient.nodeExists(sessionNode)) {
            return null;
        }
        ClientSession data = zkClient.getData(sessionNode, ClientSession.class);
        Server server = data.getServer();
        if (Objects.isNull(server)) {
            if (!zkClient.nodeExists(serverNode)) {
                return null;
            }
            server = zkClient.getData(serverNode, Server.class);
        }
        return StrUtil.builder(server.getIp()).append(StrPool.COLON).append(server.getPort()).toString();
    }
}
