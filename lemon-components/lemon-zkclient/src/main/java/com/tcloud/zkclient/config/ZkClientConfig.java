package com.tcloud.zkclient.config;

import com.tcloud.zkclient.CuratorZkClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author evans
 * @description
 * @date 2023/7/25
 */
@Configuration
public class ZkClientConfig {



    @Bean
    public CuratorZkClient curatorZkClient(ZkConfiguration zkConfiguration){
        return new CuratorZkClient(zkConfiguration.getUrl(), zkConfiguration.getSessionTimeout());
    }


}
