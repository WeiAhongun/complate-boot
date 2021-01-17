package com.shangma.cn.common.utils;

import java.util.List;
import java.util.stream.Collectors;
public class TreeUtils {
    public static<T> List<T> buildTreeData(List<T> allList, List<T> rootList) {
        rootList.forEach(item -> {
            getChildren(allList, item);
        });
        return rootList;
    }
    public static<T> void getChildren(List<T> list, T t) {
        List<T> children = list.stream().filter(item -> ReflectionUtils.getFieldValue(item,"parentId")
                .equals(ReflectionUtils.getFieldValue(t,"id"))).collect(Collectors.toList());
        if (children.size() > 0) {
            ReflectionUtils.setFieldValue(t,"children",children);
            children.forEach(item -> {
                getChildren(list, item);
            });
        }
    }
}
