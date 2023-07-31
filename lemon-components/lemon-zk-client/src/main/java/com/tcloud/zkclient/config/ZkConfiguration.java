package com.tcloud.zkclient.config;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import com.tcloud.zkclient.cli.ZkClient;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

import static com.tcloud.zkclient.common.constants.CoreConstant.DEFAULT_ROOT_PATH;
import static com.tcloud.zkclient.common.constants.CoreConstant.DEFAULT_SESSION_TIMEOUT;

/**
 * @author evans
 * @description
 * @date 2023/7/25
 */
@Data
@Configuration
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

    /**
     * ZK根节点
     */
    private String root;


    @Bean
    public ZkClient curatorZkClient() {
        return new ZkClient(this.url, this.sessionTimeout, this.root);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (CharSequenceUtil.isBlank(url)) {
            throw new RuntimeException("please set zookeeper url;");
        }
        if (Objects.isNull(sessionTimeout)) {
            this.sessionTimeout = DEFAULT_SESSION_TIMEOUT;
        }
        if (CharSequenceUtil.isBlank(root)) {
            this.root = DEFAULT_ROOT_PATH;
        } else if (!root.startsWith(StrPool.SLASH)){
            this.root = StrPool.SLASH.concat(root);
        }
    }
}
