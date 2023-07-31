package com.tcloud.zkclient.common.annotations;

/**
 * @author evans
 * @description ZK监听子节点
 * @date 2023/7/31
 */
public @interface ZkWatcherChildren {


    /**
     * 监听节点
     *
     * @return
     */
    String zParentNode();
}
