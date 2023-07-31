package com.tcloud.zkclient.config;

import com.google.common.collect.Lists;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author evans
 * @description
 * @date 2023/7/31
 */
@Component
public class ZkWatcherChildrenRegisterProcessor implements BeanPostProcessor {

    private List<PathChildrenCacheListener> watchers = Lists.newArrayList();



}
