package com.tcloud.zkclient.config;

import com.google.common.collect.Lists;
import com.tcloud.zkclient.cli.ZkClient;
import com.tcloud.zkclient.common.annotations.ZkWatcher;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author evans
 * @description
 * @date 2023/7/31
 */
@Component
public class ZkWatcherRegisterProcessor implements BeanPostProcessor {

    @Autowired
    private ZkClient zkClient;
    private List<CuratorCacheListener> watchers = Lists.newArrayList();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        ZkWatcher annotation = bean.getClass().getAnnotation(ZkWatcher.class);
        if (Objects.isNull(annotation)){
            return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
        }
        if (!CuratorCacheListener.class.isAssignableFrom(bean.getClass())){
            return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
        }
        watchers.add((CuratorCacheListener) bean);
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }


    private void buildAndListener(String path, CuratorCacheListener listener){
        CuratorCache curatorCache = CuratorCache.build(zkClient.getClient(), path);
        curatorCache.listenable().addListener(listener);
        curatorCache.start();
    }
}
