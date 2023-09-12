package com.tcloud.components.mq.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.DisposableBean;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RocketMQProducer implements DisposableBean {
    private DefaultMQProducer defaultMQProducer;
    private TransactionMQProducer transactionMQProducer;
    private final AtomicInteger transactionIndex = new AtomicInteger(0);
    private final ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    private final Integer threadSize = Runtime.getRuntime().availableProcessors();
    private String nameserverAddr;
    private String produceGroup;


    @Override
    public void destroy() throws Exception {
        if (this.defaultMQProducer != null) {
            this.defaultMQProducer.shutdown();
        }
    }

    public RocketMQProducer(String nameserverAddr, String produceGroup) {
        this.nameserverAddr = nameserverAddr;
        this.produceGroup = produceGroup;
    }

    public TransactionMQProducer getTransactionMQProducer() {
        return transactionMQProducer;
    }

    public DefaultMQProducer getDefaultMQProducer() {
        return this.defaultMQProducer;
    }

    public DefaultMQProducer initDefaultMqProducer() throws MQClientException {
        this.defaultMQProducer = createDefaultMqProducer(new DefaultMQProducer(this.produceGroup));
        this.defaultMQProducer.start();
        return this.defaultMQProducer;
    }

    public TransactionMQProducer initTransactionMQProducer() throws MQClientException {
        this.transactionMQProducer = createTransactionProducer(new TransactionMQProducer("tx" + this.produceGroup));
        this.transactionMQProducer.start();
        return this.transactionMQProducer;
    }

    public DefaultMQProducer createDefaultMqProducer(DefaultMQProducer defaultMQProducer) {
        defaultMQProducer.setNamesrvAddr(this.nameserverAddr);
        defaultMQProducer.setInstanceName(this.produceGroup);
        //客户端回调线程数，默认：，当前cpu核心数
        defaultMQProducer.setClientCallbackExecutorThreads(threadSize);
        //持久化消费者时间间隔
        defaultMQProducer.setPersistConsumerOffsetInterval(5000);
        defaultMQProducer.setSendMsgTimeout(10000);//生产发送消息超时时间
        defaultMQProducer.setCompressMsgBodyOverHowmuch(4096); //消费体容量上限，默认是4m
        defaultMQProducer.setRetryAnotherBrokerWhenNotStoreOK(false); //是否在内部发送失败时重试另一个broker
        defaultMQProducer.setRetryTimesWhenSendFailed(3);//同步模式下重试次数限制
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(3); //异步模式下重试次数限制
        return defaultMQProducer;
    }

    public TransactionMQProducer createTransactionProducer(TransactionMQProducer transactionMQProducer) {
        transactionMQProducer.setNamesrvAddr(this.nameserverAddr);
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        });

        transactionMQProducer.setExecutorService(executorService);
        transactionMQProducer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object o) {
                int value = transactionIndex.getAndIncrement();
                int status = value % 3;
                localTrans.put(msg.getTransactionId(), status);
                return LocalTransactionState.UNKNOW;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                Integer status = localTrans.get(msg.getTransactionId());
                if (null != status) {
                    switch (status) {
                        case 0:
                            return LocalTransactionState.UNKNOW;
                        case 1:
                            return LocalTransactionState.COMMIT_MESSAGE;
                        case 2:
                            return LocalTransactionState.ROLLBACK_MESSAGE;
                    }
                }
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        return transactionMQProducer;
    }

    public void setNameserverAddr(String nameserverAddr) {
        this.nameserverAddr = nameserverAddr;
    }

    public void setProduceGroup(String produceGroup) {
        this.produceGroup = produceGroup;
    }


}