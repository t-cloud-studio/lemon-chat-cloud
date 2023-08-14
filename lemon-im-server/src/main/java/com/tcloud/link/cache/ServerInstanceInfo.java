package com.tcloud.link.cache;

import com.google.common.collect.Maps;
import com.tcloud.register.domain.core.ClientSession;
import com.tcloud.register.domain.core.Server;
import lombok.Data;

import java.util.Map;
import java.util.Objects;

/**
 * 当前服务器实例信息
 *
 * @author Anker
 */
@Data
public class ServerInstanceInfo {



    private Long serverId;


    private Server server;

    /**
     * 当前服务器会话列表
     *
     * @k userId
     * @v session
     */
    private Map<Long, ClientSession> sessionMap = Maps.newConcurrentMap();


    public static volatile ServerInstanceInfo instance;

    public ServerInstanceInfo(Long serverId, Server server) {
        this.serverId = serverId;
        this.server = server;
    }

    private ServerInstanceInfo() {
    }

    public static ServerInstanceInfo initInstance(Long serverId, Server serverInfo) {
        if (instance == null) {
            synchronized (ServerInstanceInfo.class) {
                if (instance == null) {
                    instance = new ServerInstanceInfo(serverId, serverInfo);
                }
            }
        }
        return instance;
    }

    /**
     * 添加会话
     *
     * @param userId  用户id
     * @param session 会话信息
     */
    public void addSession(Long userId, ClientSession session) {
        if (Objects.isNull(session)) {
            throw new NullPointerException("the client session must not be null!!!");
        }
        sessionMap.put(userId, session);
    }

    /**
     * 移除会话
     *
     * @param userId 用户id
     */
    public void removeSession(Long userId) {
        if (!sessionMap.containsKey(userId)) {
            return;
        }
        sessionMap.remove(userId);
    }

}
