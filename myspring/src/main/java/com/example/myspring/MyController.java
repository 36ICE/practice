package com.example.myspring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/user/{name}")
    public String queryUser(@PathVariable("name") String name) {
        System.out.println("name:"+name);
        return name;
    }
//    @GetMapping("/user/**")
//    public String queryUser1() {
//        System.out.println("name:");
//        return "";
//    }
}
