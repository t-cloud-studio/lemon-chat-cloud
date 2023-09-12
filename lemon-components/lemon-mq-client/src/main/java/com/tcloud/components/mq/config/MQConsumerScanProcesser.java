package com.tcloud.components.mq.config;


import com.tcloud.components.mq.annotations.RocketMQConsumer;
import com.tcloud.components.mq.enums.MQRouter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Configuration
public class MQConsumerScanProcesser implements BeanPostProcessor {

    @Autowired
    private RocketMQConfigProperties mqProperties;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        RocketMQConsumer annotation = AnnotationUtils.findAnnotation(bean.getClass(), RocketMQConsumer.class);
        if (Objects.isNull(annotation)) {
            return bean;
        }
        // 开始创建消费者
        List<Class<?>> interfaces = Arrays.asList(bean.getClass().getInterfaces());
        if (!CollectionUtils.contains(interfaces.iterator(), MessageListenerConcurrently.class)) {
            log.error("[{}] >>> 未实现 {} 的<onMessage>方法", bean.getClass().getName(), MessageListenerConcurrently.class.getName());
            return bean;
        }
        MQRouter mqEnum = annotation.mqTopic();
        if (Objects.isNull(mqEnum)) {
            log.error("[{}] >>> 未填写对应监听MQ枚举", bean.getClass().getName());
            return bean;
        }
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(mqEnum.getGid());
        consumer.setNamesrvAddr(mqProperties.getNameServerAddr());
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe(mqEnum.getTopic(), mqEnum.getTag());
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            // 消息处理逻辑
            MessageListenerConcurrently consumerBean = (MessageListenerConcurrently) bean;
            return consumerBean.consumeMessage(msgs, context);
        });
        try {
            consumer.start();
            log.info("[MQ-Consumer:{}] >>> 已启动 -- 监听Topic:{} - GID:{} - Tag:{}", bean.getClass().getName(), mqEnum.getTopic(), mqEnum.getGid(), mqEnum.getTag());
        } catch (Exception ex) {
            log.error("[MQ-Consumer:{}] >>> 启动失败", bean.getClass().getName());
        }
        return bean;
    }
}
