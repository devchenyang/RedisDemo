package com.chenyang.spring.redisdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RedisDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(RedisDemoApplication.class, args);
        TestRedis redis = ctx.getBean(TestRedis.class);
        redis.testRedis();
    }

}
