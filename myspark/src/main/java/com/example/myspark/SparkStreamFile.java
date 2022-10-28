package com.example.myspark;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public class SparkStreamFile {

    private static String appName = "spark.streaming.demo";
    private static String master = "local[1]";
    private static String host = "localhost";
    private static int port = 9999;

    public static void main(String[] args) throws InterruptedException {
        //初始化sparkConf
        SparkConf sparkConf = new SparkConf()
                .setMaster(master)
                .setAppName(appName);

        //获得JavaStreamingContext
        JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(1));
        JavaDStream<String> stringJavaDStream = ssc.textFileStream("你的目录\\stream.txt");
        stringJavaDStream.print();

        ssc.start();
        ssc.awaitTermination();


    }
}
