package com.ithxc.blogdemo.mapper;

import com.ithxc.blogdemo.bean.Blog;
import com.ithxc.blogdemo.bean.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author hxc
 * @create 2020-03-10 0:02
 */
public interface TypeMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into t_type(name) values(#{name})")
    public void insertType(Type type);

    @Select("select * from t_type where id=#{id}")
    public Type findTypeById(Long id);

    @Select("select * from t_type")
    public List<Type> findall();

    @Select("select *,count(t.name) num from t_type t,t_blog b " +
            "where t.id=b.type_id " +
            "group by t.id " +
            "order by num desc")
    public List<Type> findtop();

    @Update("update t_type set name=#{name} where id=#{id}")
    public void updateType(Type type);

    @Delete("delete from t_type where id=#{id}")
    public void delete(Long id);

    @Select("select * from t_type where name=#{name}")
    public Type findByName(String name);
}
