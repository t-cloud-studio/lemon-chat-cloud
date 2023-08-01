package com.tcloud.zkclient.watcher;

import lombok.Data;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;

@Data
public abstract class ZkWatcherListener implements CuratorCacheListener {

    private String zkNode;


    public ZkWatcherListener(String zkNode) {
        this.zkNode = zkNode;
    }
}
