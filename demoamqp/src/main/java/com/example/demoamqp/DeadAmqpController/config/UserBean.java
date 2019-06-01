package com.example.demoamqp.DeadAmqpController.config;

import com.example.demoamqp.DeadAmqpController.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import java.util.UUID;

@Configuration
public class UserBean {


    /**
     * @Scope注解是springIoc容器中的一个作用域，在 Spring IoC 容器中具有以下几种作用域：
     * 基本作用域singleton（单例）、prototype(多例)，
     * Web 作用域（reqeust、session、globalsession），自定义作用域
     *
     *@Lazy注解用于标识bean是否需要延迟加载,第一次调用时加载
     * @return
     */
    @Lazy
    @Scope(value = "prototype")
    @Bean(value = "user0" ,name = "user0", initMethod ="initUserMethod",destroyMethod = "destoryUserMethod")
    public User setUser(){
        String id = UUID.randomUUID().toString().substring(0, 3);
        User user = new User(id,"哈哈哈","123",false);
        return user;
    }


}
