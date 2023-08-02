package com.tcloud.idgenerator.config;

import com.tcloud.idgenerator.handler.DistributedIdGenerator;
import com.tcloud.idgenerator.handler.SnowflakeIdGenerator;
import com.tcloud.zkclient.cli.ZkClient;
import com.tcloud.zkclient.common.constants.CoreConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Slf4j
@Configuration
public class IdGeneratorConfig {

    @Autowired
    private ZkClient zkClient;

    @Bean
    @Primary
    @ConditionalOnBean(ZkClient.class)
    public SnowflakeIdGenerator snowflakeIdGenerator(){
        long workerId = zkClient.getWorkerId();
        log.info("generator a worker id for this server:{}",workerId);
        return new SnowflakeIdGenerator(workerId);
    }

    @Bean
    @ConditionalOnBean(SnowflakeIdGenerator.class)
    public DistributedIdGenerator distributedIdGenerator(){
        return new DistributedIdGenerator(snowflakeIdGenerator(), zkClient, CoreConstant.DISTRIBUTED_ID_CONTAINER);
    }




}
