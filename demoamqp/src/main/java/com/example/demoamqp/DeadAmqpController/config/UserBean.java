package com.example.demoamqp.DeadAmqpController.config;

import com.example.demoamqp.DeadAmqpController.bean.User;
import com.github.pagehelper.PageHelper;
import org.aopalliance.intercept.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.*;

import java.util.Properties;
import java.util.UUID;

@Configuration
public class UserBean {


    /**
     * @Scope注解是springIoc容器中的一个作用域，在 Spring IoC 容器中具有以下几种作用域：
     * 基本作用域singleton（单例）、prototype(多例)，
     * Web 作用域（reqeust、session、globalsession），自定义作用域
     *
     *@Lazy注解用于标识bean是否需要延迟加载,第一次调用时加载
     *
     *@Conditional注解是可以根据一些自定义的条件动态的选择是否加载该bean到springIOC容器中去，
     */
    @Conditional(UserCondition.class)
    @Lazy
    @Scope(value = "prototype")
    @Bean(value = "user0" ,name = "user0", initMethod ="initUserMethod",destroyMethod = "destoryUserMethod")
    public User setUser(){
        String id = UUID.randomUUID().toString().substring(0, 3);
        User user = new User(id,"哈哈哈","123",false);
        return user;
    }




}
