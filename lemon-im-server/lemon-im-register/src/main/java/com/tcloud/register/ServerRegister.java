package com.tcloud.register;

import cn.hutool.core.text.StrPool;
import com.tcloud.register.domain.core.Server;
import com.tcloud.zkclient.cli.ZkClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 服务注册
 *
 * @author Anker
 */
@Component
@RequiredArgsConstructor
public class ServerRegister {

    private final ZkClient zkClient;



    /**
     * 服务注册
     *
     * @param server 服务信息
     */
    public void register(Server server) {
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
}
