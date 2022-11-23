package com.example.myrabbitmq.no;


import com.example.myrabbitmq.RabbitUtils;
import com.rabbitmq.client.Channel;

import static com.example.myrabbitmq.Values.HEAD_EXCHANGE;

public class Producer {


    public static void main(String[] args) throws Exception {
        //获得连接信道
        Channel channel = RabbitUtils.getChannel();
        //声明交换机
//        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.TOPIC);
        //发送消息体
        String message="你好，fanout型交换机";
        //发送消息
        channel.basicPublish(HEAD_EXCHANGE,"z.c.d",null,message.getBytes("UTF-8"));
        System.out.println("生产者发出消息:["+message+"]");

    }
}

