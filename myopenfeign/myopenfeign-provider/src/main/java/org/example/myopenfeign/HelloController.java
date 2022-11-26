package org.example.myopenfeign;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("")
public class HelloController {

    @PostMapping("/hello")
    public String hello(User user){

        return "hello "+user;
    }

    @GetMapping("/test")
    public String test(){

        return "test";
    }

}
