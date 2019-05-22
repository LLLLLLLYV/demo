package com.example.demoamqp.RedisController.config;

import com.github.xiaolyuh.aspect.LayeringAspect;
import com.github.xiaolyuh.manager.CacheManager;
import com.github.xiaolyuh.manager.LayeringCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableAspectJAutoProxy
public class LayeringCacheConfig {

    @Bean
    public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
        return new LayeringCacheManager(redisTemplate);
    }

    @Bean
    public LayeringAspect layeringAspect() {
        return new LayeringAspect();
    }
}
