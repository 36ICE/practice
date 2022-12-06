package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class TestDao {
    public String query(String msg) {
        return "msg:"+msg;
    }

}
