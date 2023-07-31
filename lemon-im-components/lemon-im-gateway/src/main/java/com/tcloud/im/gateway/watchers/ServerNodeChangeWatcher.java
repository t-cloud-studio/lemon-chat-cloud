package com.tcloud.im.gateway.watchers;

import com.tcloud.zkclient.common.annotations.ZkWatcherChildren;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

/**
 * @author evans
 * @description
 * @date 2023/7/31
 */
@ZkWatcherChildren(zParentNode = "aaa")
public class ServerNodeChangeWatcher implements PathChildrenCacheListener {


    @Override
    public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {

    }
}
