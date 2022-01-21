package com.config;


import com.entity.Carrito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() { return new JedisConnectionFactory(); }

    @Bean
    RedisTemplate<String, Carrito> redisTemplate(){
        final RedisTemplate<String, Carrito> redis=new RedisTemplate<>();
        redis.setConnectionFactory(jedisConnectionFactory());

        return redis;
    }
}
