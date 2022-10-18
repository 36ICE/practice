package com.example.mydubbo.chat.config;

import com.example.mydubbo.chat.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    static Properties properties;
    static {
        try (InputStream in=Config.class.getResourceAsStream("/application.properties")){

            properties=new Properties();
            properties.load(in);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static int getServerPort(){
        String value=properties.getProperty("server.port");
        if(value==null){
            return 9999;
        }else {
            return Integer.parseInt(value);
        }
    }

    public static Serializer.Algorithm getSerializerAlgorithm(){
        String value =properties.getProperty("serializer.algorithm");
        if(value==null){
            return Serializer.Algorithm.Java;
        }else {
            return Serializer.Algorithm.valueOf(value);
        }
    }


}
