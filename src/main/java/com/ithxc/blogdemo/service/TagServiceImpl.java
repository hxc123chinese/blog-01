package com.ithxc.blogdemo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ithxc.blogdemo.NotFoundException;
import com.ithxc.blogdemo.bean.Blog;
import com.ithxc.blogdemo.bean.Tag;
import com.ithxc.blogdemo.bean.TagNum;
import com.ithxc.blogdemo.mapper.TagMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author hxc
 * @create 2020-03-10 16:41
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;


    @Override
    public Tag addTag(Tag tag) {
        tagMapper.insertTag(tag);
        return tag;
    }

    @Override
    public Tag getTag(Long id) {
        return tagMapper.findTagById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagMapper.findByName(name);
    }

    //分页查找
    @Override
    public PageInfo<Tag> listTag(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Tag> list = tagMapper.findall();
        PageInfo<Tag> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //查找所有
    @Override
    public List<Tag> listTag() {
        return tagMapper.findall();
    }

    //通过一组id获取一组tag对象
    @Override
    public List<Tag> listTag(String ids) {
        return tagMapper.findall1(convertToList(ids));
    }
    //将字符串转换为数组的方法
    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Override
    public List<TagNum> listTagTop() {
        return tagMapper.findtop();
    }


    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagMapper.findTagById(id);
        if(t == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag, t);
        tagMapper.updateTag(tag);
        return t;
    }

    @Override
    public void deleteTag(Long id) {
        tagMapper.delete(id);
    }
}
