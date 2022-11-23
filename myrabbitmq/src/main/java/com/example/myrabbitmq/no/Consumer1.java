package com.example.myrabbitmq.no;

import com.example.myrabbitmq.RabbitUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import static com.example.myrabbitmq.Values.HEAD_EXCHANGE;


public class Consumer1 {


    //接收消息
    public static void main(String[] args) throws Exception {
        //获得连接信道
        Channel channel = RabbitUtils.getChannel();
        //队列名称
        String queueName="queue1";
//        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.DIRECT);
        //声明队列
        channel.queueDeclare(queueName,false,false,false,null);
        //绑定队列与交换机
        channel.queueBind(queueName,HEAD_EXCHANGE,"z.c.d");
        //接收消息回调函数
        DeliverCallback deliverCallback=(deliverTag, message)->{
            System.out.println("接收到从队列"+queueName+"传来到消息："+new String(message.getBody()));
        };
        //消息中断回调函数
        CancelCallback cancelCallback=(consumerTag)->{
            System.out.println("消息接收中断");
        };
        //接收消息
        channel.basicConsume(queueName,true,deliverCallback,cancelCallback);
    }
}


