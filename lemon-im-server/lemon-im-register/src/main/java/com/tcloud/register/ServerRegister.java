package com.tcloud.register;

import cn.hutool.core.text.StrPool;
import com.tcloud.register.domain.core.Server;
import com.tcloud.register.domain.core.Session;
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
     * 注册用户到服务
     *
     * @param session
     */
    public void registerSession(Session session){

    }

    /**
     * 服务注册
     *
     * @param server 服务信息
     */
    public void registerServer(Server server) {
        // 组装节点
        String nodePath = zkClient.getRootPath().concat(StrPool.SLASH).concat(server.getName());
        if (zkClient.nodeExists(nodePath)){
            // 更新
            zkClient.updateData(nodePath, server);
            return;
        }
        // 新增
        zkClient.createNode(nodePath, server);
    }


    /**
     * 服务注册
     *
     * @param serverName 目标服务名称
     */
    public void offlineServer(String serverName) {
        String nodePath = zkClient.buildPath(serverName);
        // 删除服务
        zkClient.deleteNode(nodePath);
        log.warn("im server:{} node is offline", serverName);
    }
}
