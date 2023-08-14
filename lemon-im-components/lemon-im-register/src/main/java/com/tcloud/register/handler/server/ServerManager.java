package com.tcloud.register.handler.server;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tcloud.register.domain.core.ClientSession;
import com.tcloud.register.domain.core.Server;
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
     * @return {@link Set<Server>} 服务集合
     */
    public List<Server> getAllServers(){
        Set<String> servers = zkClient.getRootChildrenData();
        if (CollUtil.isEmpty(servers)) {
            return null;
        }
        List<Server> serverSet = Lists.newArrayList();
        for (String serverJson : servers) {
            Server server = JSON.parseObject(serverJson, Server.class);
            serverSet.add(server);
        }
        return serverSet;
    }


    /**
     * 从Zk拉取所有服务信息
     *
     * @return {@link Set<Server>} 服务集合
     */
    public Server find(String serverId){
        String serverNode = zkClient.getRootPath().concat(StrPool.SLASH).concat(serverId);
        if (!zkClient.nodeExists(serverNode)){
            return null;
        }
        return zkClient.getData(serverNode, Server.class);
    }


}
