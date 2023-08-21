package com.tcloud.im.lsb.listener;

import com.tcloud.im.common.utils.JsonUtil;
import com.tcloud.im.lsb.policy.AbstractBalancePolicy;
import com.tcloud.register.domain.ServerInfo;
import com.tcloud.zkclient.cli.ZkClient;
import com.tcloud.zkclient.watcher.ZkChildWatcherListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author evans
 * @description 服务节点监听器
 * @date 2023/7/31
 */
@Slf4j
@Component
public class ServerNodeChangeListener extends ZkChildWatcherListener {


    private final ZkClient zkClient;
    private final AbstractBalancePolicy balancePolicy;

    /**
     * 构造
     *
     * @param zkClient
     */
    @Autowired
    public ServerNodeChangeListener(ZkClient zkClient, AbstractBalancePolicy balancePolicy) {
        this.zkClient = zkClient;
        this.balancePolicy = balancePolicy;
        init();
    }

    @Override
    public void init() {
        String rootPath = zkClient.getRootPath();
        super.setZkNode(rootPath);
        log.info("ServerNodeChangeListener is init");
    }

    @Override
    public void event(Type type, ChildData childData, ChildData childData1) {
        log.info("type:{} - data1:{} - data2:{}", type, childData, childData1);
        // just listener child node
        if (super.isRootPath(childData, childData1)) {
            return;
        }
        if (type.equals(Type.NODE_CREATED)) {
            byte[] data = childData1.getData();
            ServerInfo server = JsonUtil.parseByteToObject(data, ServerInfo.class);
            log.info("the server :{} is register!!", server);
            balancePolicy.addServer(server);
        } else if (type.equals(Type.NODE_DELETED)) {
            byte[] data = childData.getData();
            ServerInfo server = JsonUtil.parseByteToObject(data, ServerInfo.class);
            log.info("the server :{} is offline!!", server);
            balancePolicy.remove(server.getServerId());
        }
    }

}
