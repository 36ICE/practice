package com.example.mydubbo.chat.rpcservice;

public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "你好 " + name;
    }
}
