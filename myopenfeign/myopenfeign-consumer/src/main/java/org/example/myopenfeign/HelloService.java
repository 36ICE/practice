package org.example.myopenfeign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "localhost:8081/",name = "a")
@Service
public interface HelloService {

    @RequestMapping(value = "/hello")
    String hello( CUser user);

    @RequestMapping(value="/test",method= RequestMethod.GET)
    String test();
}
