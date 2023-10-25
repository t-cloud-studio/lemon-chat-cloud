package com.tcloud.im.router.consumer;

import com.tcloud.common.obj.msg.WsMessage;
import com.tcloud.components.mq.annotations.RocketMQConsumer;
import com.tcloud.components.mq.enums.MQRouter;
import com.tcloud.components.mq.utils.MQBodyUtil;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

@RocketMQConsumer(target = MQRouter.GROUP_MSG)
public class GroupChatMsgRouterConsumer implements MessageListenerConcurrently {


    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        list.forEach(msg -> {
            WsMessage message = MQBodyUtil.parseToObject(msg.getBody(), WsMessage.class);

        });

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
