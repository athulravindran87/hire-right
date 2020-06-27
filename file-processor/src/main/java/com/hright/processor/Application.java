package com.hright.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@ComponentScan(basePackages = "com.hright")
@EnableKafka
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
