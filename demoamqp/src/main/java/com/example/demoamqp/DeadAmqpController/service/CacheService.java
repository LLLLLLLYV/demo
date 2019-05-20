package com.example.demoamqp.DeadAmqpController.service;


import com.example.demoamqp.DeadAmqpController.bean.User;

public interface CacheService {

    User queryUserById(String id);
    void addUserByUser(User user);
    void updateUserById(String id);
}
