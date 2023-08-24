package com.tcloud.register.handler.server;

import com.tcloud.register.domain.ServerInfo;
import com.tcloud.zkclient.cli.ZkClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 服务注册
 *
 * @author Anker
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ServerRegister {

    private final ZkClient zkClient;


    /**
     * 服务注册到注册中心
     *
     * @param server 服务信息
     */
    public void register(ServerInfo server) {
        // 组装节点
        String nodePath = zkClient.buildPath(server.getServerId().toString());
        if (zkClient.nodeExists(nodePath)){
            // 更新
            zkClient.updateData(nodePath, server);
            return;
        }
        // 新增
        zkClient.createNode(nodePath, server);
    }


    /**
     * 服务下线，将该服务从注册中心移除
     *
     * @param serverId 目标服务名称
     */
    public void offline(Long serverId) {
        String nodePath = zkClient.buildPath(serverId.toString());
        // 删除服务
        zkClient.deleteNode(nodePath);
        log.warn("im server:{} node is offline", serverId);
    }
}
