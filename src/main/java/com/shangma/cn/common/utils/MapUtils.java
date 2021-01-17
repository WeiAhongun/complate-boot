package com.shangma.cn.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
public class MapUtils {
    public static Map<String, Object> getObjectToMap(Object obj){
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        Field[] declaredFields1 = clazz.getSuperclass().getDeclaredFields();
        //把数组转成list集合
        List<Field> fields = Arrays.asList(declaredFields);
        List<Field> fields1 = Arrays.asList(declaredFields1);
        //把两个list集合合并
        List<Field> list = new ArrayList<>();
        list.addAll(fields);
        list.addAll(fields1);
        list.forEach(field ->{
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value == null){
                value = "";
            }
            map.put(fieldName, value);
        });
        return map;
    }

    //Map转Object
    public static Object mapToObject(Map<Object, Object> map, Class<?> beanClass) throws Exception {
        if (map == null){
            return null;
        }
        Object obj = beanClass.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            if (map.containsKey(field.getName())) {
                field.set(obj, map.get(field.getName()));
            }
        }
        return obj;
    }
}
