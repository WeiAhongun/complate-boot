package com.shangma.cn.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;
@Slf4j
public class JsonUtils {
    static ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 对象转字符串
     */
    public static String objToStr(Object obj){
        if(obj instanceof String){
            return (String) obj;
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("json转换错误");
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 字符串转对象
     */
    public static <T> T strToObj(String jsonStr,Class<T> clazz){
        T t = null;
        try {
            t = objectMapper.readValue(jsonStr, clazz);
        } catch (JsonProcessingException e) {
            log.error("字符串转对象错误");
            e.printStackTrace();
        }
        return t;
    }
    /**
     * 字符串转List
     */
    public static <T> List<T> strToList(String str,Class<T> clazz){
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        List<T> list = null;
        try {
            list = objectMapper.readValue(str,collectionType);
        } catch (JsonProcessingException e) {
            log.error("字符串转List错误");
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 字符串转map
     */
    public static <K,V> Map<K,V> strToMap(String jsonStr,Class<K> kClass,Class<V> vClass){
        MapType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, kClass, vClass);
        Map<K,V> map = null;
        try {
            map = objectMapper.readValue(jsonStr, mapType);
        } catch (JsonProcessingException e) {
            log.error("字符串转map错误");
            e.printStackTrace();
        }
        return map;
    }
}















