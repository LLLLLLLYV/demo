package com.example.demoamqp.DeadAmqpController.web;

import com.example.demoamqp.DeadAmqpController.bean.User;
import com.example.demoamqp.DeadAmqpController.config.WebExceptionAspest;
import com.example.demoamqp.DeadAmqpController.service.impl.CacheServiceImpl;
import com.example.demoamqp.DeadAmqpController.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class AmqpController {

    private static final Logger logger = LoggerFactory.getLogger(AmqpController.class);
    @Autowired
    private CacheServiceImpl cacheService;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${server.port}")
    private String port;

    @RequestMapping(value = "/haha" ,method = RequestMethod.GET)
    public String haha(@Valid String id){

        User user = cacheService.queryUserById(id);
        System.out.println(user);
       // int a=1/0;
        return port;
        //return "xx";
    }

    @RequestMapping(value = "/sethaha" ,method = RequestMethod.POST)
    public String sethaha(@Valid User user){
        /*if(result.hasFieldErrors()){
            List<FieldError> errorList = result.getFieldErrors();
            //通过断言抛出参数不合法的异常
            errorList.stream().forEach(item -> Assert.isTrue(false,item.getDefaultMessage()));
        }*/

        cacheService.addUserByUser(user);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_D_TO_D,RabbitConfig.ROUTINGKEY_DD ,user
                ,new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //消息持久化
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                //消息过期时间
                message.getMessageProperties().setExpiration(1000*15+"");
                return message;
            }
        });
        System.out.println(user);
        return user.toString();
    }

    @RequestMapping("/sethaha1")
    public String sethaha1(){

        User user = new User();
        user.setId(UUID.randomUUID().toString().substring(0,5));
        user.setUserName("张三"+UUID.randomUUID().toString().substring(0,2));
        user.setPassword("123456");
        cacheService.addUserByUser(user);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_D_TO_D,RabbitConfig.ROUTINGKEY_DD ,user);
        System.out.println(user);
        return user.toString();
    }


    @RequestMapping("/zhifu")
    public void zhifu(String id){
        cacheService.updateUserById(id);
    }
}
