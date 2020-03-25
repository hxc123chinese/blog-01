package com.ithxc.blogdemo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ithxc.blogdemo.bean.Tag;
import com.ithxc.blogdemo.bean.TagNum;
import com.ithxc.blogdemo.bean.Type;

import java.util.Collection;
import java.util.List;

/**
 * @author hxc
 * @create 2020-03-10 16:35
 */
public interface TagService {

    //新增
    Tag addTag(Tag tag);

    //查询单个
    Tag getTag(Long id);
    Tag getTagByName(String name);

    //查询所有
    PageInfo<Tag> listTag(int pageNum, int pageSize);
    List<Tag> listTag();
    List<Tag> listTag(String ids);
    List<TagNum> listTagTop();

    //修改
    Tag updateTag(Long id,Tag tag);

    //删除
    void deleteTag(Long id);
}
