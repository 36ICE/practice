package org.example.myopenfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ConsumerApp {


    public static void main(String[] args) {

        SpringApplication.run(ConsumerApp.class,args);
    }
}
