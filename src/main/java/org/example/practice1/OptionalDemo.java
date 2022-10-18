package org.example.practice1;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class A{

    public String str;
}
class A1 extends A{

}

public class OptionalDemo {

    @Test
    public void test(){
        List<Optional<String>> list = new ArrayList<>();
        list.add(Optional.ofNullable("a"));
        list.add(Optional.ofNullable("b"));
        list.add(Optional.ofNullable("c"));
        list.stream()
//                .map(this::getUserById)    // 获得 Stream<Optional<User>>
                .filter(Optional::isPresent)
                .map(Optional::get) // Stream 的 flatMap 方法将多个流合成一个流
                .collect(Collectors.toList())
                .forEach(sout -> System.out.println(sout));

        List<String> stringList=new ArrayList<>();
        stringList.add("a1");
        stringList.add("b1");
        stringList.add("c1");

        stringList.stream().forEach(System.out::println);
        stringList.parallelStream().map(Collectors::joining).forEach(System.out::print);

        System.out.println();

        System.out.println(0.2+0.1);




        System.out.println(get(A.class));
    }

    public <T> T get(Class<T> clazz){

        return JSONObject.parseObject("{'str':'a'}",clazz);

    }



}
