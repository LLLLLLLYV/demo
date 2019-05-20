package com.example.demoamqp.DeadAmqpController.service.impl;

import com.example.demoamqp.DeadAmqpController.bean.User;
import com.example.demoamqp.DeadAmqpController.dao.CacheDao;
import com.example.demoamqp.DeadAmqpController.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CacheServiceImpl implements CacheService {
    @Resource
    private CacheDao ca;

    @Override
    public User queryUserById(String id) {
        User user = ca.queryUserById(id);
        return user;
    }

    @Override
    public void addUserByUser(User user) {
        ca.addUserByUser(user);
    }

    @Override
    public void updateUserById(String id) {
        ca.updateUserById(id);
    }
}
