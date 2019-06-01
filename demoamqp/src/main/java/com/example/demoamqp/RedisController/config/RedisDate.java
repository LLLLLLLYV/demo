package com.example.demoamqp.RedisController.config;

import com.example.demoamqp.DeadAmqpController.bean.User;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
/**
 * @PropertySource  指明加载类路径下的哪个配置文件来注入值
 *
 * @ConfigurationProperties(prefix = "spring.redis")
 * 默认从全局配置文件中获取值然后进行注入
 * prefix = "user" 表示 将配置文件中key为user的下面所有的属性与本类属性进行一一映射注入值，如果配置文件中
 * 不存在"user"的key，则不会为POJO注入值，属性值仍然为默认值
 *
 *
 * @Import(value = User.class) 通过导入的方式实现把实例加入springIOC容器中
 */
@Import(value = User.class)
@PropertySource(value = {"classpath:bootstrap.yml"})
@Component
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisDate implements Serializable {

    private String host;

    private int port;

    private int timeout;

    private int database;

    private Map<String,Integer> jedis;

}
