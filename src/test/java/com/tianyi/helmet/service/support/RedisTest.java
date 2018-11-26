package com.tianyi.helmet.service.support;

import com.tianyi.helmet.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by liuhanc on 2017/10/30.
 */
public class RedisTest extends BaseServiceTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testSetData(){
        redisTemplate.opsForValue().set("dev","val-"+System.currentTimeMillis());
    }

}
