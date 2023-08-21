package com.tcloud.register.domain;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 服务器详情
 *
 * @author Anker
 */
@Data
@Builder
public class ServerInfo implements Comparable<ServerInfo> {

    /**
     * 服务名称,将作为ZK节点名称
     */
    private String name;
    /**
     * 服务ip地址
     */
    private String ip;
    /**
     * 服务端口信息
     */
    private int port;
    /**
     * 服务客户端连接数
     */
    private AtomicInteger connections;
    /**
     * 节点id
     */
    private Long serverId;


    @Override
    public int compareTo(ServerInfo other) {
        // 比较连接数
        return Integer.compare(this.connections.get(), other.connections.get());
    }

    public Integer connectionsIncr(Integer num) {
        connections = new AtomicInteger(connections.addAndGet(num));
        return connections.get();
    }
}
