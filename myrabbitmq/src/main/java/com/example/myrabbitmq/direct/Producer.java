package com.example.myrabbitmq.direct;


import com.example.myrabbitmq.RabbitUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import static com.example.myrabbitmq.Values.DIRECT_EXCHANGE;

public class Producer {


    public static void main(String[] args) throws Exception {
        //获得连接信道
        Channel channel = RabbitUtils.getChannel();
        //声明交换机
        channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);
        //发送消息体
        String message="你好，fanout型交换机";
        //发送消息
        channel.basicPublish(DIRECT_EXCHANGE,"z.c.d",null,message.getBytes("UTF-8"));
        System.out.println("生产者发出消息:["+message+"]");

    }
}

