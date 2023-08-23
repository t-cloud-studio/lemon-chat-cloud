package com.tcloud.im.gateway.websocket.cache;

import com.google.common.collect.Maps;
import com.tcloud.register.domain.ServerInfo;
import io.netty.channel.socket.nio.NioSocketChannel;
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


    private ServerInfo server;


    public static volatile ServerInstanceInfo instance;

    public ServerInstanceInfo(Long serverId, ServerInfo server) {
        this.serverId = serverId;
        this.server = server;
    }

    private ServerInstanceInfo() {
    }

    /**
     * 初始化实例
     *
     * @param serverId      分布式id
     * @param serverInfo    服务信息
     * @return {@link ServerInstanceInfo} 实例
     */
    public static ServerInstanceInfo initInstance(Long serverId, ServerInfo serverInfo) {
        if (instance == null) {
            synchronized (ServerInstanceInfo.class) {
                if (instance == null) {
                    instance = new ServerInstanceInfo(serverId, serverInfo);
                }
            }
        }
        return instance;
    }



    public String getHost() {
        return server.getIp();
    }

    public Integer getPort() {
        return server.getPort();
    }


}
