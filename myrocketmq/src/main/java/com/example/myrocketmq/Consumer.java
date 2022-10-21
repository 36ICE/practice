package com.example.myrocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

public class Consumer {

    public static final String CONSUMER_GROUP = "group_test";
    public static final String DEFAULT_NAMESRVADDR = "0.0.0.0:9876";
    public static final String TOPIC_1 = "Topic_ASYNC";
    public static final String TOPIC_2 = "Topic_SYNC";
    public static final String TOPIC_3 = "Topic_ONE_WAY";



    public static void main(String[] args) throws Exception{

        // 初始化一个确切的消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);

        // nameserver服务地址
        consumer.setNamesrvAddr(DEFAULT_NAMESRVADDR);

        /**
         * 消费最新消息
         * CONSUME_FROM_LAST_OFFSET,
         * 从头开始消费
         *CONSUME_FROM_FIRST_OFFSET,
         *CONSUME_FROM_TIMESTAMP;
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 订阅主题
        consumer.subscribe(TOPIC_1, "*");
        consumer.subscribe(TOPIC_2, "*");
        consumer.subscribe(TOPIC_3, "*");

        // 注册回调函数
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), list);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        // 初始化消费者
        consumer.start();

        System.out.printf("Consumer Started.%n");
    }

}
