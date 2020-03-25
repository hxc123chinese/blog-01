package com.ithxc.blogdemo.service;

import com.github.pagehelper.PageInfo;
import com.ithxc.blogdemo.bean.Blog;
import com.ithxc.blogdemo.bean.BlogQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author hxc
 * @create 2020-03-11 21:39
 */
public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    PageInfo<Blog> listBlog(int pageNum, int pageSize, BlogQuery blogQuery);
    PageInfo<Blog> listBlog(int pageNum, int pageSize);
    PageInfo<Blog> listBlog(String query,int pageNum, int pageSize);
    PageInfo<Blog> listBlog(int pageNum, int pageSize,Long tagId);
    List<Blog> listBlog();

    Map<String,List<Blog>> archiveBlog();

    Blog addBlog(Blog blog);

    Blog updateBlog(Blog blog,Long id);

    void deleteBlog(Long id);
}
