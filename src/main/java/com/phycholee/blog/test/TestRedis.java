package com.phycholee.blog.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by PhychoLee on 2017/4/7 23:27.
 * Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedis {

    private StringRedisTemplate redis;

    @Autowired
    public void setRedis(StringRedisTemplate redis) {
        this.redis = redis;
        //泛型设置成Long后必须更改对应的序列化方案
//        redis.setKeySerializer(new JdkSerializationRedisSerializer());
    }

    @Test
    public void testSet(){
        redis.boundValueOps("aa").set("llfss");

        String s = redis.boundValueOps("aa").get();
        System.out.println(s);
    }
}
