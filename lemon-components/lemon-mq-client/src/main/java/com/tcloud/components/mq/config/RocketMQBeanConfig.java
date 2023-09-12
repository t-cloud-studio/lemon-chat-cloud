package com.tcloud.components.mq.config;

import com.tcloud.components.mq.cli.MQProducerClient;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMQBeanConfig {


    @Autowired
    private RocketMQConfigProperties properties;


    @Bean
    public RocketMQProducer rocketMQProducer() {
        return new RocketMQProducer(properties.getNameServerAddr(), properties.getProducerGroup());

    }


    @Bean
    @ConditionalOnBean(RocketMQProducer.class)
    public DefaultMQProducer defaultMQProducer(RocketMQProducer rocketMQProducer) throws MQClientException {
        // 初始化默认生产者
        return rocketMQProducer.initDefaultMqProducer();
    }

    @Bean
    @ConditionalOnBean(RocketMQProducer.class)
    public TransactionMQProducer transactionMQProducer(RocketMQProducer rocketMQProducer) throws MQClientException {
        // 初始化默认生产者
        return rocketMQProducer.initTransactionMQProducer();
    }

    @Bean
    public MQProducerClient mqProducerClient(DefaultMQProducer defaultMQProducer, TransactionMQProducer transactionMQProducer){
        return new MQProducerClient(defaultMQProducer, transactionMQProducer);
    }
}
