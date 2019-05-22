package com.example.demoamqp.RedisController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
public class DemoRedisController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping(value = "setRedit",method = RequestMethod.GET)
    public Long setRedit(String key){
        Long expire = redisTemplate.getExpire(key);
        return expire;
    }

    @RequestMapping(value = "setRedit2",method = RequestMethod.GET)
    public String setRedit2(String key,String value){
        Jedis jedis = jedisPool.getResource();
        String set = jedis.set(key, value);
        jedis.close();
        return set;
    }


}
