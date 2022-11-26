package org.example.myopenfeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ConsumerController {

    @Autowired
    HelloService helloService;

    @GetMapping("/consumer/hello")
    public String hello( ){
        CUser cUser=new CUser();
        cUser.setAddress("江西");
        cUser.setName("zcd");
        System.out.println(cUser);
        return helloService.hello(cUser);

    }

}
