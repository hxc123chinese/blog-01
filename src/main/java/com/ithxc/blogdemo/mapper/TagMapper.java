package com.ithxc.blogdemo.mapper;

import com.ithxc.blogdemo.bean.Tag;
import com.ithxc.blogdemo.bean.TagNum;
import com.ithxc.blogdemo.bean.Type;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;

/**
 * @author hxc
 * @create 2020-03-10 16:22
 */
public interface TagMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into t_tag(name) values(#{name})")
    public void insertTag(Tag tag);

    @Select("select * from t_tag where id=#{id}")
    public Tag findTagById(Long id);

    @Select("select * from t_tag")
    public List<Tag> findall();

    @Select("select tag_id,count(blog_id) num from t_blog_tags " +
            "group by tag_id " +
            "order by num desc")
    public List<TagNum> findtop();

    @Select("<script>" +
            "select * from t_tag where id in " +
            "<foreach collection='list' open='(' item='id' separator=',' close=')'> " +
            "#{list}" +
            "</foreach>" +
            "</script>")
    public List<Tag> findall1(List<Long> list);

    @Update("update t_tag set name=#{name} where id=#{id}")
    public void updateTag(Tag tag);

    @Delete("delete from t_tag where id=#{id}")
    public void delete(Long id);

    @Select("select * from t_tag where name=#{name}")
    public Tag findByName(String name);
}
