package com.tcloud.im.lsb.watchers;

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
public class ServerNodeChangeWatcher extends ZkChildWatcherListener {


    @Autowired
    private ZkClient zkClient;


    @Override
    public void init() {
        String rootPath = zkClient.getRootPath();
        super.setZkNode(rootPath);
    }

    @Override
    public void event(Type type, ChildData childData, ChildData childData1) {
        log.info("type:{} - data1:{} - data2:{}", type, childData,childData1);
    }

}
