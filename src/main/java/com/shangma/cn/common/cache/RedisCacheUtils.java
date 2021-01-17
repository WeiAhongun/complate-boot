package com.shangma.cn.common.cache;

import com.shangma.cn.common.container.SpringBeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisCacheUtils {
    private static final long time = 5;
    private static StringRedisTemplate stringRedisTemplate = SpringBeanUtils.getBean(StringRedisTemplate.class);

    public static void setRedisCache(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public static void setRedisCacheWithDefultTime(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value,time, TimeUnit.MINUTES);
    }

    public static void setRedisCacheWithCustomTime(String key, String value,long time) {
        stringRedisTemplate.opsForValue().set(key, value,time, TimeUnit.MINUTES);
    }

    public static String getRedisCache(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public static void deleteRedisCache(String key) {
         stringRedisTemplate.delete(key);
    }
}
