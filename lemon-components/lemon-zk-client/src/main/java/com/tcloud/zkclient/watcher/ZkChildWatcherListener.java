package com.tcloud.zkclient.watcher;

import lombok.Data;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.springframework.beans.factory.DisposableBean;

@Data
public abstract class ZkChildWatcherListener implements CuratorCacheListener {


    private String zkNode;

    private CuratorCache.Options options;

    /**
     * 子类继承-为监听节点zkNode赋值
     */
    public abstract void init();

}
