package com.example.demoamqp.DeadAmqpController.dao;


import com.example.demoamqp.DeadAmqpController.bean.User;

public interface CacheDao {

    User queryUserById(String id);

    void addUserByUser(User user);

    void updateUserById(String id);
}
