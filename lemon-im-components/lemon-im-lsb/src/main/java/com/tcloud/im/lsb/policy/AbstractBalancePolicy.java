package com.tcloud.im.lsb.policy;

import com.tcloud.register.domain.core.Server;
import lombok.Data;

import java.util.List;

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
    private static List<Server> servers;


    public abstract Server balance();

    /**
     * 新增服务时
     * @param server
     */
    public void addServer(Server server) {
        servers.add(server);
    }

    public static void removeServer(Server server) {
        servers.remove(server);
    }

    public static void setServers(List<Server> allServers) {
        servers = allServers;
    }

    protected List<Server> getServers() {
        return servers;
    }
}
