package com.tcloud.im.router.consumer;

import com.tcloud.components.mq.annotations.RocketMQConsumer;
import com.tcloud.components.mq.enums.MQRouter;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

@RocketMQConsumer(target = MQRouter.NOTIFICATION_MSG)
public class UserNotificationMsgRouterConsumer implements MessageListenerConcurrently {

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
