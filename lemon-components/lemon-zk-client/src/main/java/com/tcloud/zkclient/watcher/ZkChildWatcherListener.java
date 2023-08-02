package com.tcloud.zkclient.watcher;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.Data;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.springframework.beans.factory.DisposableBean;

import java.util.Objects;

@Data
public abstract class ZkChildWatcherListener implements CuratorCacheListener {


    private String zkNode;

    private CuratorCache.Options options;

    /**
     * 子类继承-为监听节点zkNode赋值
     */
    public abstract void init();


    public boolean isRootPath(ChildData... childData) {
        for (ChildData childDatum : childData) {
            if (Objects.isNull(childDatum)) {
                continue;
            }
            if (zkNode.equals(childDatum.getPath())) {
                return true;
            }
        }
        return false;
    }
}
