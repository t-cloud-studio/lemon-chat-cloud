package com.tcloud.im.lsb.policy;

import cn.hutool.core.collection.CollUtil;
import com.tcloud.register.domain.ServerInfo;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * 抽象权重策略
 *
 * @author Anker
 */
@Data
public abstract class AbstractBalancePolicy {

    /**
     * 所有服务
     */
    private static List<ServerInfo> servers;


    public abstract ServerInfo balance();

    /**
     * 新增服务时
     * @param server
     */
    public synchronized void addServer(ServerInfo server) {
        if (CollUtil.isEmpty(servers)){
            servers.add(server);
            return;
        }
        ServerInfo existsSever = servers.stream().filter(s -> server.getServerId().equals(s.getServerId())).findAny().orElse(null);
        if (Objects.isNull(existsSever)){
            servers.add(server);
        }
    }

    /**
     * 从服务列表中移除
     *
     * @param serverId
     */
    public void remove(Long serverId) {
        if (CollUtil.isEmpty(servers)){
            return;
        }
        ServerInfo server = servers.stream().filter(s -> serverId.equals(s.getServerId())).findAny().orElse(null);
        if (Objects.isNull(server)){
            return;
        }
        servers.remove(server);
    }

    public static void removeServer(ServerInfo server) {
        servers.remove(server);
    }

    public static void setServers(List<ServerInfo> allServers) {
        servers = allServers;
    }

    protected List<ServerInfo> getServers() {
        return servers;
    }
}
