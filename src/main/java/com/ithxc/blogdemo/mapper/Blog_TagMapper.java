package com.ithxc.blogdemo.mapper;

import com.ithxc.blogdemo.bean.Blog;
import com.ithxc.blogdemo.bean.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author hxc
 * @create 2020-03-13 22:54
 */
public interface Blog_TagMapper {

    @Insert("<script>" +
            "insert into t_blog_tags(blog_id,tag_id) values" +
            "<foreach collection='tagIds' item='tagId' index='index' separator=','> " +
            "(#{blogId}, #{tagId})" +
            "</foreach>" +
            "</script>")
    public void insert(Long blogId ,List<Long> tagIds);

    @Delete("delete from t_blog_tags where blog_id=#{blogId}")
    public void delete(Long blogId);

    @Select("select * from t_tag where id in(select tag_id from t_blog_tags where blog_id=#{blogId})")
    public List<Tag> selectTagsIdByBlogId(Long blogId);

    @Select("select * from t_blog where id in(select blog_id from t_blog_tags where tag_id=#{tagId})")
    @Results({
            @Result(id=true,property="id",column="id"),
            @Result(property = "type",column = "type_id",
                    one=@One(select = "com.ithxc.blogdemo.mapper.TypeMapper.findTypeById")),
            @Result(property = "user",column = "user_id",
                    one=@One(select = "com.ithxc.blogdemo.mapper.UserMapper.findById")),
            @Result(property = "tags",column = "id",javaType = List.class,
                    many = @Many(select = "com.ithxc.blogdemo.mapper.Blog_TagMapper.selectTagsIdByBlogId"))
    })
    public List<Blog> selectBlogsByTagId(Long tagId);

}
