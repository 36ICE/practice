package com.example.mydubbo.chat;

import java.io.*;
import java.nio.charset.StandardCharsets;

public interface Serializer {
    //反序列化方法
    <T> T deserializer(Class<T> clazz, byte[] bytes);

    //序列化方法
    <T> byte[] serialize(T object);

    enum Algorithm implements Serializer {
        Java {
            @Override
            public <T> T deserializer(Class<T> clazz, byte[] bytes) {

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    return (T) objectInputStream.readObject();
                } catch (ClassNotFoundException | IOException e) {
                    throw new RuntimeException("反序列化失败");
                }

            }

            @Override
            public <T> byte[] serialize(T object) {


                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    objectOutputStream.writeObject(object);
                    return byteArrayOutputStream.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException("序列化失败");
                }
            }
        },
        Json {
            @Override
            public <T> T deserializer(Class<T> clazz, byte[] bytes) {

                String s = new String(bytes, StandardCharsets.UTF_8);
                try {
                   return com.alibaba.fastjson.JSON.parseObject(s, clazz);
                }catch (Exception e){
                    throw new RuntimeException("反序列化异常");
                }

            }

            @Override
            public <T> byte[] serialize(T object) {

                String s = com.alibaba.fastjson.JSON.toJSONString(object);
                if (s != null) {
                    return s.getBytes(StandardCharsets.UTF_8);
                }else {
                    throw new RuntimeException("序列化失败");
                }
            }
        }
    }


}
