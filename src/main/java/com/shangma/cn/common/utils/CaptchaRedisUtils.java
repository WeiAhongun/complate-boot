package com.shangma.cn.common.utils;

import com.shangma.cn.common.cache.RedisCacheUtils;
import com.shangma.cn.common.constant.RedisKeyConstant;

public class CaptchaRedisUtils {

    public static void setCaptchaRedis(String uuid,String code){
        RedisCacheUtils.setRedisCache(RedisKeyConstant.CAPTCHA_CODE+uuid,code);
    }

    public static String getCaptchaRedis(String uuid){
        return RedisCacheUtils.getRedisCache(RedisKeyConstant.CAPTCHA_CODE+uuid);
    }
}
