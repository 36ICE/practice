package com.example.myredission;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;

public class MyTopic {
    public static RedissonClient redisson;

    @BeforeAll
    public static void init(){
        redisson=Redisson.create();
    }
    @Test
    public void testTopic1(){
        RedissonClient redisson=Redisson.create();
        RAtomicLong rAtomicLong=redisson.getAtomicLong("zcd");
        System.out.println(rAtomicLong.incrementAndGet());
    }

    @Test
    public void testpublish(){
        RTopic topic=redisson.getTopic("topic1");
        for (int i = 0; i < 20; i++) {
            topic.publish(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testConsumer(){
        RTopic topic=redisson.getTopic("topic1");
        topic.addListener(Integer.class,new MessageListener<Integer>() {
            @Override
            public void onMessage(CharSequence charSequence, Integer integer) {
                System.out.println(integer);
            }
        });

    }
}
