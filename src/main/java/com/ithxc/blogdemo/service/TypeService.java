package com.ithxc.blogdemo.service;

import com.github.pagehelper.PageInfo;
import com.ithxc.blogdemo.bean.Type;

import java.util.List;

/**
 * @author hxc
 * @create 2020-03-09 23:25
 */
public interface TypeService {

    //新增
    Type addType(Type type);

    //查询
    Type getType(Long id);
    Type getTypeByName(String name);

    //查询所有,分页
    PageInfo<Type> listType(int pageNum, int pageSize);
    //查询所有
    List<Type> listType();
    //查询按数量排序后的前多少条
    List<Type> listTypeTop();

    //修改
    Type updateType(Long id,Type type);

    //删除
    void deleteType(Long id);

}
