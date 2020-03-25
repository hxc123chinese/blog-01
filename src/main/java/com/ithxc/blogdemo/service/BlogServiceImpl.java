package com.ithxc.blogdemo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ithxc.blogdemo.NotFoundException;
import com.ithxc.blogdemo.bean.Blog;
import com.ithxc.blogdemo.bean.BlogQuery;
import com.ithxc.blogdemo.mapper.BlogMapper;
import com.ithxc.blogdemo.mapper.Blog_TagMapper;
import com.ithxc.blogdemo.util.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author hxc
 * @create 2020-03-11 21:45
 */
@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private Blog_TagMapper blog_tagMapper;

    @Override
    public Blog getBlog(Long id) {
        return blogMapper.findBlogById(id);
    }

    @Override
    public Blog getAndConvert(Long id) {
        blogMapper.updateViews(id);
        Blog blog = blogMapper.findBlogById(id);
        if(blog==null){
            throw new NotFoundException("该博客不存在");
        }
        Blog b=new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = blog.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        return b;
    }

    @Override
    public PageInfo<Blog> listBlog(int pageNum, int pageSize, BlogQuery blogQuery) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> list = blogMapper.findall(blogQuery);
        PageInfo<Blog> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<Blog> listBlog(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> list = blogMapper.findallByPublished();
        PageInfo<Blog> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<Blog> listBlog(String query, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> list = blogMapper.findallByQuery(query);
        PageInfo<Blog> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<Blog> listBlog(int pageNum, int pageSize, Long tagId) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> list =blog_tagMapper.selectBlogsByTagId(tagId);
        PageInfo<Blog> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public List<Blog> listBlog() {
        return blogMapper.findByUpdatetime();
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        List<String> years=blogMapper.findGroupYears();
        for (String year: years) {
            List<Blog> blogs=blogMapper.findBlogByYear(year);
            map.put(year, blogs);
        }
        return map;
    }

    @Override
    public Blog addBlog(Blog blog) {
        if(blog.getId() == null){//新增
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);       //初始化浏览次数为0
            blogMapper.insertBlog(blog);
            blog_tagMapper.insert(blog.getId(),convertToList(blog.getTagIds()));
        }else {//修改
            blog.setUpdateTime(new Date());
            blogMapper.updateBlog(blog);
            blog_tagMapper.delete(blog.getId());
            blog_tagMapper.insert(blog.getId(),convertToList(blog.getTagIds()));
        }
        return blog;
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
    public Blog updateBlog(Blog blog, Long id) {
        Blog b = blogMapper.findBlogById(id);
        if(b==null){
            throw new NotFoundException("不存在该博客");
        }
        BeanUtils.copyProperties(blog, b);
        blogMapper.updateBlog(blog);
        return blog;
    }

    @Override
    public void deleteBlog(Long id) {
        blogMapper.delete(id);
    }
}
