package org.example.practice2;


import javax.annotation.processing.Processor;
import java.util.ServiceLoader;

@Getter
public class TestAno {

    private String a;

    public static void main(String[] args) {
        System.out.printf("1");
        //SPI机制全称Service Provider Interface
        ServiceLoader<Processor> serviceLoader=ServiceLoader.load(Processor.class);
        serviceLoader.forEach(x->System.out.println(x.getSupportedAnnotationTypes()));
    }
}
