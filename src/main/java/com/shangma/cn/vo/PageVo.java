package com.shangma.cn.vo;

import lombok.Data;

import java.util.List;

/**
 * 开发者：辉哥
 * 特点： 辉哥很帅
 * 开发时间：2021/1/1 11:42
 * 文件说明：
 */
@Data
public class PageVo<T> {

    private long total;

    private List<T> list;
}
