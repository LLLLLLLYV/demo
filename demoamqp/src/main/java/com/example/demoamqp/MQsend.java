package com.example.demoamqp;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQsend {
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate2;
    public void send(String ex,String mes){
        rabbitTemplate.convertAndSend(ex,mes);
    }

    public void send2(String ex,String key,Object mes){
        rabbitTemplate2.convertAndSend(ex,key,mes);
    }
}
