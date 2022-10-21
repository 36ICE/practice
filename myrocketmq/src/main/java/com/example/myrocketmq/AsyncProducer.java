package com.example.myrocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AsyncProducer {

    public static final int MESSAGE_COUNT = 1000;
    public static final String PRODUCER_GROUP = "group_test";
    public static final String DEFAULT_NAMESRVADDR = "127.0.0.1:9876";
    public static final String TOPIC = "Topic_ASYNC";
    public static final String TAG = "TagA";

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);

//        producer.createTopic();
        //客户端设置的默认队列数为4，最后服务器选择最小的参数
//        producer.setDefaultTopicQueueNums(4);
        producer.setClientCallbackExecutorThreads(1);

        final CountDownLatch countDownLatch = new CountDownLatch(MESSAGE_COUNT);
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            try {
                final int index = i;

                Message msg = new Message(TOPIC, TAG, "order", "Hello World".getBytes(RemotingHelper.DEFAULT_CHARSET));
                producer.send(msg, new SendCallback() {

                    @Override
                    public void onSuccess(SendResult sendResult) {
                        countDownLatch.countDown();
                        System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        countDownLatch.countDown();
                        System.out.printf("%-10d Exception %s %n", index, throwable);
                        throwable.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        countDownLatch.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }

}
