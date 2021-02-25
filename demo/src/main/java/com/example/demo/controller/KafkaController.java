package com.example.demo.controller;

import com.example.demo.kafka.KafkaProducer;
import com.example.demo.kafka2.producer.Kafka2Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private Kafka2Producer kafka2Producer;
    @RequestMapping("send")
    public String sendMsg(String msg) {
        kafkaProducer.send(msg);
        return "成功";
    }

    @RequestMapping("send2")
    public String send2Msg(String msg) {
        kafka2Producer.send(msg);
        return "成功";
    }
}
