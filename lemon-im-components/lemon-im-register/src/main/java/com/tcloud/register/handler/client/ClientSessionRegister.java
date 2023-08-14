package com.tcloud.register.handler.client;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.tcloud.im.common.constants.NumConstant;
import com.tcloud.im.common.exceptions.ServerOfflineException;
import com.tcloud.redis.config.RedisClient;
import com.tcloud.register.domain.core.ClientSession;
import com.tcloud.register.domain.core.Server;
import com.tcloud.zkclient.cli.ZkClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.Objects;

import static com.tcloud.im.common.constants.ImRedisKeyPatternConstant.CLIENT_SESSION_REGISTER_CENTER_KEY;

/**
 * 客户端会话注册器
 *
 * @author Anker
 */
@Slf4j
@RequiredArgsConstructor
public class ClientSessionRegister implements InitializingBean {


    private final ZkClient zkClient;

    private final RedisClient redisClient;


    public boolean register(String serverId, ClientSession session){
        String serverNode = StrUtil.builder(zkClient.getRootPath()).append(StrPool.SLASH).append(serverId).toString();
        if (!zkClient.nodeExists(serverNode)){
            throw new ServerOfflineException("server is already offline!!");
        }
        String sessionRegisterNode = StrUtil.builder(serverNode).append(StrPool.SLASH).append(session.getSessionId()).toString();
        // 将该会话注册至该服务下
        zkClient.createNode(sessionRegisterNode, session);
        // 该服务连接数+1
        Server server = zkClient.getData(serverNode, Server.class);
        server.connectionsIncr(NumConstant.ONE);
        zkClient.updateData(serverNode, server);
        // 将该会话注册至注册表
        redisClient.hPut(CLIENT_SESSION_REGISTER_CENTER_KEY, session.getSessionId(), serverId);
        return true;
    }


    public boolean offline(String sessionId){
        Object o = redisClient.hGet(CLIENT_SESSION_REGISTER_CENTER_KEY, sessionId);
        if (Objects.isNull(o)){
            log.warn("this session {} is not exists in register center", sessionId);
            return false;
        }
        // 将对应所在服务的连接数-1
        String serverNode = StrUtil.builder(zkClient.getRootPath()).append(StrPool.SLASH).append(o).toString();
        if (zkClient.nodeExists(serverNode)){
            Server data = zkClient.getData(serverNode, Server.class);
            Integer connections = data.connectionsIncr(NumConstant.M_ONE);
            log.warn("session :{} is offline , the server :{} current connections is :{}", sessionId, o, connections);
            zkClient.updateData(serverNode, data);
        }
        // 从注册中心移除
        redisClient.hDelete(CLIENT_SESSION_REGISTER_CENTER_KEY, sessionId);
        return true;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (Objects.isNull(redisClient)){
            throw new NullPointerException("the redis client must be configuration in your project");
        }
    }
}
