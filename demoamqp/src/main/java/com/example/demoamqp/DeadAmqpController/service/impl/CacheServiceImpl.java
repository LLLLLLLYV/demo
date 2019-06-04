package com.example.demoamqp.DeadAmqpController.service.impl;

import com.example.demoamqp.DeadAmqpController.bean.User;
import com.example.demoamqp.DeadAmqpController.dao.CacheDao;
import com.example.demoamqp.DeadAmqpController.service.CacheService;
import com.github.xiaolyuh.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
@Service
public class CacheServiceImpl implements CacheService {
    @Autowired
    private CacheDao ca;

    @Override
    @Cacheable(value = "user", key = "#id", depict = "用户信息缓存",
            firstCache = @FirstCache(expireTime = 4, timeUnit = TimeUnit.MINUTES),
            secondaryCache = @SecondaryCache(expireTime = 10, preloadTime = 3, forceRefresh = true, timeUnit = TimeUnit.MINUTES,isAllowNullValue = true, magnification = 10))

    public User queryUserById(String id) {
        User user = ca.queryUserById(id);
        System.out.println("--------------查询成功");
        return user;
    }

    @Override
    @CacheEvict(value = "user", key = "#user.id")
    public void addUserByUser(User user) {
        ca.addUserByUser(user);
    }

    @Override
    public void updateUserById(String id) {
        ca.updateUserById(id);
    }

    @Override
    public int updateUserByDate(String name, String value, String id) {
        int i = ca.updateUserByDate(name, value, id);
        return i;
    }
}
