package com.ithxc.blogdemo.bean;

/**
 * @author hxc
 * @create 2020-03-14 16:42
 */
public class TagNum {
    private Long tagId;
    private Integer num;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "TagNum{" +
                "tagId=" + tagId +
                ", num=" + num +
                '}';
    }
}
