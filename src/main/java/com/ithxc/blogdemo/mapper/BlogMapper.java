package com.ithxc.blogdemo.mapper;

import com.ithxc.blogdemo.bean.Blog;
import com.ithxc.blogdemo.bean.BlogQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author hxc
 * @create 2020-03-11 21:45
 */
public interface BlogMapper {

    @Select("select * from t_blog where id=#{id}")
    @Results({
            @Result(id=true,property="id",column="id"),//对多查询一定要加这句，不然id为空
            @Result(property = "type",column = "type_id",
                    one=@One(select = "com.ithxc.blogdemo.mapper.TypeMapper.findTypeById")),
            @Result(property = "user",column = "user_id",
                    one=@One(select = "com.ithxc.blogdemo.mapper.UserMapper.findById")),
            @Result(property = "tags",column = "id",javaType = List.class,
                    many = @Many(select = "com.ithxc.blogdemo.mapper.Blog_TagMapper.selectTagsIdByBlogId"))
    })
    Blog findBlogById(Long id);


    @Select("<script>" +
            "select * from t_blog" +
            "<where> " +
            "<if test='title != null and title != &quot;&quot;'>" +
            "and title like CONCAT('%',#{title},'%') " +
            "</if>" +
            "<if test='typeId != null and typeId != &quot;&quot;'>" +
            "and type_id=#{typeId} " +
            "</if>" +
            "<if test='recommend != null and recommend != &quot;&quot;'>" +
            "and recommend=#{recommend} " +
            "</if>" +
            "</where>" +
            "</script>")
    @Results({
            @Result(property = "type",column = "type_id",
                    one=@One(select = "com.ithxc.blogdemo.mapper.TypeMapper.findTypeById")),
            @Result(property = "user",column = "user_id",
                    one=@One(select = "com.ithxc.blogdemo.mapper.UserMapper.findById"))
    })
    List<Blog> findall(BlogQuery blogQuery);

    @Select("select * from t_blog where published=true")
    @Results({
            @Result(id=true,property="id",column="id"),
            @Result(property = "type",column = "type_id",
                    one=@One(select = "com.ithxc.blogdemo.mapper.TypeMapper.findTypeById")),
            @Result(property = "user",column = "user_id",
                    one=@One(select = "com.ithxc.blogdemo.mapper.UserMapper.findById"))
    })
    List<Blog> findallByPublished();

    @Select("select * from t_blog " +
            "where title like CONCAT('%',#{query},'%') or " +
            "content like CONCAT('%',#{query},'%') or " +
            "description like CONCAT('%',#{query},'%')")
    @Results({
            @Result(id=true,property="id",column="id"),
            @Result(property = "type",column = "type_id",
                    one=@One(select = "com.ithxc.blogdemo.mapper.TypeMapper.findTypeById")),
            @Result(property = "user",column = "user_id",
                    one=@One(select = "com.ithxc.blogdemo.mapper.UserMapper.findById"))
    })
    List<Blog> findallByQuery(String query);

    @Select("select * from t_blog " +
            "where recommend=true and published=true " +
            "order by update_time desc")
    List<Blog> findByUpdatetime();

    @Select("select date_format(b.update_time,'%Y') as year " +
            "from t_blog b " +
            "group by year " +
            "order by year desc")
    List<String> findGroupYears();

    @Select("Select * from t_blog b where date_format(b.update_time,'%Y')=#{year}")
    @Results({
            @Result(id=true,property="id",column="id"),//对多查询一定要加这句，不然id为空
            @Result(property = "type",column = "type_id",
                    one=@One(select = "com.ithxc.blogdemo.mapper.TypeMapper.findTypeById")),
            @Result(property = "user",column = "user_id",
                    one=@One(select = "com.ithxc.blogdemo.mapper.UserMapper.findById")),
            @Result(property = "tags",column = "id",javaType = List.class,
                    many = @Many(select = "com.ithxc.blogdemo.mapper.Blog_TagMapper.selectTagsIdByBlogId"))
    })
    List<Blog> findBlogByYear(String year);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into t_blog" +
            "(title,content,first_picture,flag,views,appreciation,share_Statement,commentabled,published,recommend,create_time,update_time,description,type_id,user_id)" +
            " values(#{title},#{content},#{firstPicture},#{flag},#{views},#{appreciation},#{shareStatement},#{commentabled},#{published},#{recommend},#{createTime},#{updateTime},#{description},#{type.id},#{user.id})")
    void insertBlog(Blog blog);

    @Update("update t_blog set" +
            " title=#{title},content=#{content},first_picture=#{firstPicture}," +
            "flag=#{flag},appreciation=#{appreciation},share_Statement=#{shareStatement}," +
            "commentabled=#{commentabled},published=#{published},recommend=#{recommend}," +
            "update_time=#{updateTime},description=#{description},type_id=#{type.id},user_id=#{user.id} where id=#{id}")
    void updateBlog(Blog blog);

    @Update("update t_blog set views=views + 1 where id=#{id}")
    void updateViews(Long id);

    @Delete("delete from t_blog where id=#{id}")
    void delete(Long id);
}
