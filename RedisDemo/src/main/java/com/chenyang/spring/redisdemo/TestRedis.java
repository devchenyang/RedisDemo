package com.chenyang.spring.redisdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TestRedis {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    @Qualifier("ooxx")
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public void testRedis() {
//        stringRedisTemplate.opsForValue().set("hello", "china");
//        System.out.println(stringRedisTemplate.opsForValue().get("hello"));

        RedisConnection conn = redisTemplate.getConnectionFactory().getConnection();

        conn.set("hello02".getBytes(), "world".getBytes());
        System.out.println(new String(conn.get("hello02".getBytes())));

        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        hash.put("jack", "name", "xcm;");
        hash.put("jack", "age", "22");
        System.out.println(hash.entries("jack"));


        Person person = new Person();
        person.setName("tom");
        person.setAge(16);

        Jackson2HashMapper jm = new Jackson2HashMapper(objectMapper, false);

        stringRedisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));

        stringRedisTemplate.opsForHash().putAll("tom", jm.toHash(person));
        Map map = stringRedisTemplate.opsForHash().entries("tom");

        Person person1 = objectMapper.convertValue(map, Person.class);
        System.out.println(person1);


        stringRedisTemplate.convertAndSend("ooxx", "hello");

        RedisConnection cc = stringRedisTemplate.getConnectionFactory().getConnection();
        cc.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] bytes) {
                System.out.println(new String(message.getBody()));
            }
        }, "ooxx".getBytes());

        while (true) {
            stringRedisTemplate.convertAndSend("ooxx", "hello from ziji");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
