package com.ithxc.blogdemo.bean;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hxc
 * @create 2020-03-08 16:10
 * 标签类
 */
public class Tag {
    private Long id;
    @NotBlank(message = "标签名称不能为空！")
    private String name;
    private Integer num;
    private List<Blog> blogs=new ArrayList<>();


    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", num=" + num +
                ", blogs=" + blogs +
                '}';
    }
}
