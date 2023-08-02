package com.tcloud.zkclient.config;

import com.google.common.collect.Lists;
import com.tcloud.zkclient.cli.ZkClient;
import com.tcloud.zkclient.watcher.ZkChildWatcherListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author evans
 * @description
 * @date 2023/7/31
 */
@Slf4j
@Component
public class ZkWatcherChildrenRegisterProcessor implements BeanPostProcessor {

    @Autowired
    private ZkClient zkClient;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!(bean instanceof ZkChildWatcherListener listener)) {
            return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
        }
        CuratorCache.Options option = null;
        if (Objects.nonNull(listener.getOptions())) {
            option = listener.getOptions();
        }
        log.info("注册监听器：{}", listener);
        // 执行监听器注册
        CuratorCache curatorCache = CuratorCache.build(zkClient.getClient(), listener.getZkNode(), option);
        curatorCache.listenable().addListener(listener);
        curatorCache.start();
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
