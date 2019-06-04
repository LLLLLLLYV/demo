package com.example.demoamqp.DeadAmqpController.service;


import com.example.demoamqp.DeadAmqpController.bean.User;
import org.springframework.stereotype.Service;

public interface CacheService {

    User queryUserById(String id);
    void addUserByUser(User user);
    void updateUserById(String id);
    int updateUserByDate(String name,String value,String id);
}
