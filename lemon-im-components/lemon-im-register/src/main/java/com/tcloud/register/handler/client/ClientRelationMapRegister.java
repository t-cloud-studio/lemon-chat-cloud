package com.tcloud.register.handler.client;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.tcloud.im.common.constants.NumConstant;
import com.tcloud.im.common.exceptions.ServerOfflineException;
import com.tcloud.redis.config.RedisClient;
import com.tcloud.register.domain.ClientRouteServerInfo;
import com.tcloud.register.domain.ServerInfo;
import com.tcloud.zkclient.cli.ZkClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static com.tcloud.im.common.constants.ImRedisKeyPatternConstant.CLIENT_SESSION_REGISTER_CENTER_KEY;

/**
 * 客户端会话注册器
 *
 * @author Anker
 */
@Slf4j
@RequiredArgsConstructor
public class ClientRelationMapRegister {


    private final ZkClient zkClient;

    private final RedisClient redisClient;


    public boolean register(String serverId, ClientRouteServerInfo session){
        String serverNode = StrUtil.builder(zkClient.getRootPath()).append(StrPool.SLASH).append(serverId).toString();
        if (!zkClient.nodeExists(serverNode)){
            throw new ServerOfflineException("server is already offline!!");
        }
        // 该服务连接数+1
        ServerInfo server = zkClient.getData(serverNode, ServerInfo.class);
        Integer connections = server.connectionsIncr(NumConstant.ONE);
        zkClient.updateData(serverNode, server);
        log.warn("session :{} online , the server :{} current connections is :{}", session.getUserId(), serverId, connections);
        // 将该会话注册至注册表
        redisClient.hPut(CLIENT_SESSION_REGISTER_CENTER_KEY, session.getUserId().toString(), serverId);
        return true;
    }


    public boolean offline(String userId){
        Object serverId = redisClient.hGet(CLIENT_SESSION_REGISTER_CENTER_KEY, userId);
        if (Objects.isNull(serverId)){
            log.warn("this session {} is not exists in register center", userId);
            return false;
        }
        // 将对应所在服务的连接数-1
        String serverNode = StrUtil.builder(zkClient.getRootPath()).append(StrPool.SLASH).append(serverId).toString();
        if (zkClient.nodeExists(serverNode)){
            ServerInfo data = zkClient.getData(serverNode, ServerInfo.class);
            Integer connections = data.connectionsIncr(NumConstant.M_ONE);
            log.warn("session :{} is offline , the server :{} current connections is :{}", userId, serverId, connections);
            zkClient.updateData(serverNode, data);
        }
        // 从注册中心移除
        redisClient.hDelete(CLIENT_SESSION_REGISTER_CENTER_KEY, userId);
        return true;
    }
}
