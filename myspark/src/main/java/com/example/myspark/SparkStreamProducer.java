package com.example.myspark;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class SparkStreamProducer {

    public static final String PRODUCER_GROUP = "groupId";
    public static final String DEFAULT_NAMESRVADDR = "127.0.0.1:9876";
    public static final String TOPIC = "topics";
    public static final String TAG = "TagA";

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
        producer.start();
        //即使使用了32，但还是使用客户端和服务器两者配置的最小参数
        producer.setDefaultTopicQueueNums(32);
        for (int i = 0; i < 100; i++) {
            System.out.println("Hello World" + i);
            Message msg = new Message(TOPIC, TAG, ("Hello World" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.sendOneway(msg);
            Thread.sleep(1000);
        }
        Thread.sleep(5000);
        producer.shutdown();
    }

}
