package com.shangma.cn.service.base;

import com.shangma.cn.mapper.base.BaseMapper;
import com.shangma.cn.vo.PageVo;

import java.util.List;

/**
 * 开发者：辉哥
 * 特点： 辉哥很帅
 * 开发时间：2021/1/4 17:27
 * 文件说明：
 */
public interface BaseService<T> {


    PageVo<T> findAll();

    PageVo<T> findAll(Object example);

    T findById(Long id);

    int addEntity(T entity);

    int updateEntity(T entity);

    int deleteById(Long id);

    int batchDeleteByIds(List<Long> ids);

    PageVo<T> setPage(List<T> list);

    BaseMapper<T> getBaseMapper();

    List<T> getAll();
}
