package com.tcloud.zkclient.config;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;

/**
 * @author evans
 * @description
 * @date 2023/7/25
 */
@Data
@ConfigurationProperties("zookeeper")
public class ZkConfiguration implements InitializingBean {

    /**
     * 连接地址
     * http://host:2021
     */
    private String url;

    /**
     * 会话及连接超时时间
     * 毫秒
     */
    private Integer sessionTimeout;


    @Override
    public void afterPropertiesSet() throws Exception {
        if (CharSequenceUtil.isBlank(url)){
            throw new RuntimeException("please set zookeeper url;");
        }
        if (Objects.isNull(sessionTimeout)){
            this.sessionTimeout = 1000;
        }
    }
}
