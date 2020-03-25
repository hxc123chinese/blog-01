package com.ithxc.blogdemo.mapper;

import com.ithxc.blogdemo.bean.Blog;
import com.ithxc.blogdemo.bean.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author hxc
 * @create 2020-03-15 19:41
 */
public interface CommentMapper {

    @Select("select * from t_comment where blog_id=#{blogId} " +
            "and parent_comment_id is null " +
            "order by create_time asc")
    @Results({
            @Result(id=true,property="id",column="id"),
            @Result(property = "blog",column = "blog_id",javaType = Blog.class,
                    one = @One(select = "com.ithxc.blogdemo.mapper.BlogMapper.findBlogById")),
            @Result(property = "replyComments",column = "id",javaType = List.class,
                    many = @Many(select = "com.ithxc.blogdemo.mapper.CommentMapper.findReply"))
    })
    List<Comment> findByBolgId(Long blogId);

    //给父类Id找到评论的父类
    @Select("select * from t_comment where id=#{parentId}")
    Comment findOne(Long parentId);

    @Select("select * from t_comment where parent_comment_id=#{id}")
    @Results({
            @Result(id=true,property="id",column="id"),
            @Result(property = "parentComment",column = "parent_comment_id",javaType = Comment.class,
                    one = @One(select = "com.ithxc.blogdemo.mapper.CommentMapper.findOne")),
            @Result(property = "blog",column = "blog_id",javaType = Blog.class,
                    one = @One(select = "com.ithxc.blogdemo.mapper.BlogMapper.findBlogById")),
            @Result(property = "replyComments",column = "id",javaType = List.class,
                    many = @Many(select = "com.ithxc.blogdemo.mapper.CommentMapper.findReply"))
    })
    List<Comment> findReply(Long id);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into t_comment(avatar,content,create_time,email,nickname,blog_id,parent_comment_id,admin_comment) " +
            "values(#{avatar},#{content},#{createTime},#{email},#{nickname},#{blog.id},#{parentComment.id},#{adminComment})")
    void insertComment(Comment comment);
}
