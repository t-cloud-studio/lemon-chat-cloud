package com.tcloud.zkclient.common.annotations;

import java.lang.annotation.*;

/**
 * @author evans
 * @description Zk节点监听器
 * @date 2023/7/31
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZkWatcher {


    /**
     * 监听节点
     *
     * @return
     */
    String zNode();


}
