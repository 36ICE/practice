package com.example.myspark;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public class SparkStreamSocket {


    private static String appName = "spark.streaming.demo";
    private static String master = "local[*]";
    private static String host = "localhost";
    private static int port = 9999;

    public static void main(String[] args) throws InterruptedException {
        //初始化sparkConf
        SparkConf sparkConf = new SparkConf()
                .setMaster(master)
                .setAppName(appName);

        //获得JavaStreamingContext
        JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(3));

        //设置checkpointer，下次执行实现高可用。前提是代码不修改，修改之后再打包执行是找不到那个目录的，因为目录与jar的MD5信息绑定了
//        ssc.checkpoint("");
        //从socket源获取数据
        JavaReceiverInputDStream<String> lines = ssc.socketTextStream(host, port);

        lines.foreachRDD(v->v.foreach(x-> System.out.println(x)));
        //拆分行成单词
//        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
//            public Iterator<String> call(String s) throws Exception {
//                return Arrays.asList(s.split(" ")).iterator();
//            }
//        });
//
//        //计算每个单词出现的个数
//        JavaPairDStream<String, Integer> wordCounts = words.mapToPair(new PairFunction<String, String, Integer>() {
//            public Tuple2<String, Integer> call(String s) throws Exception {
//                return new Tuple2<String, Integer>(s, 1);
//            }
//        }).reduceByKey((Function2<Integer, Integer, Integer>) (integer, integer2) -> integer + integer2);
//
////        输出结果
//        wordCounts.print();

        //开始作业
        ssc.start();

        ssc.awaitTermination();

    }

}
