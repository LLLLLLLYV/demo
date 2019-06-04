package com.example.demoamqp.DeadAmqpController.dao;


import com.example.demoamqp.DeadAmqpController.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheDao {

    User queryUserById(String id);

    void addUserByUser(User user);

    void updateUserById(String id);

    int updateUserByDate(@Param("name") String name, @Param("value") String value,@Param("id") String id);
}
