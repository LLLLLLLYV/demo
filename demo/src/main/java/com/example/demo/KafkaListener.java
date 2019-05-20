package com.example.demo;

public class KafkaListener {

    @org.springframework.kafka.annotation.KafkaListener(id="foo",topics = "test")
    public void listem(String bytes){
        System.out.println(bytes);
    }
}
