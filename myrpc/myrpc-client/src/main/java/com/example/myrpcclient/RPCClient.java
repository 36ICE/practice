package com.example.myrpcclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RPCClient {

    public static void main(String[] args) {



    }


    public <T> T getService(Class<?> clazz){
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //1.构造请求

                //2.请求

                //3.得到响应

                //4.



                return null;
            }
        });
    }

    /**
     * ================
     * | 数据包长度（4字节）|命令字（1字节）|数据包内容（数据包长内容）
     * ===============
     *
     *
     *
     */
    public void start(){
        //编码、解码协议
        Bootstrap channel = new Bootstrap().group(new NioEventLoopGroup());

    }

}
