package com.tcloud.register.manager.server;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrPool;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.tcloud.register.domain.ServerInfo;
import com.tcloud.zkclient.cli.ZkClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 服务管理器
 *
 * @author Anker
 */
@Component
@RequiredArgsConstructor
public class ServerManager {

    private final ZkClient zkClient;

    /**
     * 从Zk拉取所有服务信息
     *
     * @return {@link Set< ServerInfo >} 服务集合
     */
    public List<ServerInfo> getAllServers(){
        Set<String> servers = zkClient.getRootChildrenData();
        if (CollUtil.isEmpty(servers)) {
            return null;
        }
        List<ServerInfo> serverSet = Lists.newArrayList();
        for (String serverJson : servers) {
            ServerInfo server = JSON.parseObject(serverJson, ServerInfo.class);
            serverSet.add(server);
        }
        return serverSet;
    }


    /**
     * 从Zk拉取所有服务信息
     *
     * @return {@link Set< ServerInfo >} 服务集合
     */
    public ServerInfo find(String serverId){
        String serverNode = zkClient.getRootPath().concat(StrPool.SLASH).concat(serverId);
        if (!zkClient.nodeExists(serverNode)){
            return null;
        }
        return zkClient.getData(serverNode, ServerInfo.class);
    }


}
