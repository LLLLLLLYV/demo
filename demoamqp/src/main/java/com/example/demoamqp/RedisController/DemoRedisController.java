package com.example.demoamqp.RedisController;

import com.example.demoamqp.DeadAmqpController.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.Map;

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

    @RequestMapping(value = "setRedit2",method = RequestMethod.POST)
    public String setRedit2(String key,@RequestBody User user){

        Jedis jedis = jedisPool.getResource();
        String set = jedis.set(key.getBytes(), serialize(user));
        jedis.close();
        return set;
    }

    @RequestMapping(value = "setHset",method = RequestMethod.POST)
    public Long setHset(@RequestBody User user){
        Jedis jedis = jedisPool.getResource();

        jedis.hset("User"+user.getId(),"id",user.getId());
        jedis.hset("User"+user.getId(),"password",user.getPassword());
        jedis.hset("User"+user.getId(),"userName",user.getUserName());
        Long status = jedis.hset("User" + user.getId(), "status", user.getStatus() + "");
        jedis.close();
        return status;
    }

    @RequestMapping(value = "getHset",method = RequestMethod.POST)
    public Map setHset(@RequestParam("id") String id){
        Jedis jedis = jedisPool.getResource();
        Map<String, String> stringStringMap = jedis.hgetAll("User" + id);
        jedis.close();
        return stringStringMap;
    }

    @RequestMapping(value = "getStr",method = RequestMethod.POST)
    public String getStr(@RequestParam("key") String key){
        Jedis jedis = jedisPool.getResource();
        byte[] bytes = jedis.get(key.getBytes());
        User unserizlize = (User)unserizlize(bytes);
        jedis.close();
        return unserizlize.toString();
    }

    //序列化
    public static byte [] serialize(Object obj){
        ObjectOutputStream obi=null;
        ByteArrayOutputStream bai=null;
        try {
            bai=new ByteArrayOutputStream();
            obi=new ObjectOutputStream(bai);
            obi.writeObject(obj);
            byte[] byt=bai.toByteArray();
            return byt;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //反序列化
    public static Object unserizlize(byte[] byt){
        ObjectInputStream oii=null;
        ByteArrayInputStream bis=null;
        bis=new ByteArrayInputStream(byt);
        try {
            oii=new ObjectInputStream(bis);
            Object obj=oii.readObject();
            return obj;
        } catch (Exception e) {

            e.printStackTrace();
        }


        return null;
    }
}
