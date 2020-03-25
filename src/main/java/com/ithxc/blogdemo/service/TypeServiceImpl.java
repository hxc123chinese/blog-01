package com.ithxc.blogdemo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ithxc.blogdemo.NotFoundException;
import com.ithxc.blogdemo.bean.Type;
import com.ithxc.blogdemo.mapper.TypeMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author hxc
 * @create 2020-03-09 23:59
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Transactional
    @Override
    public Type addType(Type type) {
        typeMapper.insertType(type);
        return type;
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeMapper.findTypeById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeMapper.findByName(name);
    }

    @Transactional
    @Override
    public PageInfo<Type> listType(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Type> list = typeMapper.findall();
        PageInfo<Type> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public List<Type> listType() {
        return typeMapper.findall();
    }

    @Override
    public List<Type> listTypeTop() {
        return typeMapper.findtop();
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t=typeMapper.findTypeById(id);
        if(t == null){
            throw new NotFoundException("不存在该类型");
        }
        //将type里面的值复制到t
        BeanUtils.copyProperties(type, t);
        typeMapper.updateType(type);
        return t;
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeMapper.delete(id);
    }
}
