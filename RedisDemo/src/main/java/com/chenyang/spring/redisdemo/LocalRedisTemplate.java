package com.chenyang.spring.redisdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class LocalRedisTemplate {

    @Bean
    public StringRedisTemplate ooxx(RedisConnectionFactory fc, ObjectMapper mapper) {
        StringRedisTemplate tp = new StringRedisTemplate(fc);
        tp.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        return tp;
    }
}
