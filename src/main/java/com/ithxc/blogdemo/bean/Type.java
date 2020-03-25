package com.ithxc.blogdemo.bean;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hxc
 * @create 2020-03-08 16:00
 *
 * 分类类
 */
public class Type {

    private Long id;    //主键，id

    @NotBlank(message = "分类名称不能为空！")
    private String name;//分类的名称
    private Integer num;

    private List<Blog> blogs=new ArrayList<>();

    public Type() {
    }

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
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", num=" + num +
                ", blogs=" + blogs +
                '}';
    }
}
