package com.example.myspark;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spark.ConsumerStrategy;
import org.apache.rocketmq.spark.LocationStrategy;
import org.apache.rocketmq.spark.RocketMqUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RocketMQSparkStream {
    private static String appName = "spark.streaming.demo";
    private static String master = "local[*]";

    public static void main(String[] args) throws InterruptedException {
        //初始化sparkConf
        SparkConf sparkConf = new SparkConf()
                .setMaster(master)
                .setAppName(appName);

        //获得JavaStreamingContext
        JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(3));


        Map optionParams =new HashMap<String,String>();
        optionParams.put("nameserver.addr","192.168.31.121:9876");
        JavaInputDStream<MessageExt> stream = RocketMqUtils
                .createJavaMQPullStream(ssc, "groupId",
                        Collections.singleton("topics"), ConsumerStrategy.lastest(),
                        true, false, false,
                        LocationStrategy.PreferConsistent(),optionParams);

        stream.foreachRDD(new VoidFunction<JavaRDD<MessageExt>>() {
            @Override
            public void call(JavaRDD<MessageExt> messageExtJavaRDD) throws Exception {
                messageExtJavaRDD.foreach(new VoidFunction<MessageExt>() {
                    @Override
                    public void call(MessageExt messageExt) throws Exception {
                        System.out.println(messageExt.toString());
                    }
                });
            }
        });

        //开始作业
        ssc.start();

        ssc.awaitTermination();
    }
}
