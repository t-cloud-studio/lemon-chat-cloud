package com.tcloud.components.mq.cli;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

public class MQProducerClient {

    private final DefaultMQProducer defaultMQProducer;
    private final TransactionMQProducer transactionMQProducer;


    public MQProducerClient(DefaultMQProducer defaultMQProducer, TransactionMQProducer transactionMQProducer) {
        this.defaultMQProducer = defaultMQProducer;
        this.transactionMQProducer = transactionMQProducer;
    }

    public void sendMessage(String topic, String msg) throws Exception {
        this.sendMessage(topic, "", msg);
    }

    public void sendMessage(String topic, String tags, String msg) throws Exception {
        this.sendMessage(topic, tags, "", msg);
    }

    public void sendMessage(String topic, String tags, String keys, String msg) throws Exception {
        Message message = new Message(topic, tags, keys, msg.getBytes(StandardCharsets.UTF_8));
        this.defaultMQProducer.send(message);
    }

    public void sendAsyncMessage(String topic, String msg, SendCallback sendCallback) throws Exception {
        this.sendAsyncMessage(topic, "", msg, sendCallback);
    }

    public void sendAsyncMessage(String topic, String tags, String msg, SendCallback sendCallback) throws Exception {
        this.sendAsyncMessage(topic, tags, "", msg, sendCallback);
    }

    /**
     * @param topic
     * @param tags
     * @param keys         索引建，空格分隔，快速检索到消息
     * @param msg
     * @param sendCallback
     * @throws Exception
     */
    public void sendAsyncMessage(String topic, String tags, String keys, String msg, SendCallback sendCallback) throws Exception {
        Message message = new Message(topic, tags, keys, msg.getBytes(StandardCharsets.UTF_8));
        message.isWaitStoreMsgOK();
        this.defaultMQProducer.send(message, sendCallback);
    }

    public void sendDelayMessage(String topic, String msg, int delayLevel) throws Exception {
        Message message = new Message(topic, msg.getBytes(StandardCharsets.UTF_8));
        message.setDelayTimeLevel(delayLevel);
        this.defaultMQProducer.send(message);
    }

    public void sendOrderMessage(String topic, String msg, String key) throws Exception {
        Message message = new Message(topic, msg.getBytes(StandardCharsets.UTF_8));
        this.defaultMQProducer.send(message, new SelectMessageQueueByHash(), key);
    }

    public void sendOrderDelayMessage(String topic, String msg, int delayLevel, String key) throws Exception {
        Message message = new Message(topic, msg.getBytes(StandardCharsets.UTF_8));
        message.setDelayTimeLevel(delayLevel);
        this.defaultMQProducer.send(message, new SelectMessageQueueByHash(), key);
    }

    public void sendTransactionMessage(String topic, String msg) throws Exception {
        Message message = new Message(topic, msg.getBytes(StandardCharsets.UTF_8));
        this.transactionMQProducer.sendMessageInTransaction(message, null);
    }
}
