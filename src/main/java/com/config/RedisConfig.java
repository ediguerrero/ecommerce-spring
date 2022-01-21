package com.config;


import com.entity.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() { return new JedisConnectionFactory(); }

    @Bean
    RedisTemplate<String, Car> redisTemplate(){
        final RedisTemplate<String, Car> redis=new RedisTemplate<>();
        redis.setConnectionFactory(jedisConnectionFactory());

        return redis;
    }
}
