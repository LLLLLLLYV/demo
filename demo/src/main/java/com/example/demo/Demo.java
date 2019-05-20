package com.example.demo;

import org.apache.logging.log4j.Level;
import org.slf4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
@Controller
public class Demo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(KafkaConfig.class);
        KafkaTemplate<Integer, String> kafkaTemplate = (KafkaTemplate<Integer, String>) ctx.getBean("kafkaTemplate");
        String data="aaa";
        ListenableFuture<SendResult<Integer, String>> send = kafkaTemplate.send("test", 1, data);
        send.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            public void onFailure(Throwable throwable) {

            }

            public void onSuccess(SendResult<Integer, String> integerStringSendResult) {
                System.out.println(("success"));
            }
        });
        ctx.close();
    }
}
