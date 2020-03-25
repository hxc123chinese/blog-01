package com.ithxc.blogdemo.mapper;

import com.ithxc.blogdemo.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author hxc
 * @create 2020-03-08 22:23
 */
public interface UserMapper {

    //登录验证
    @Select("select * from t_user where username = #{username} and password = #{password}")
    public User login(String username,String password);

    @Select("select * from t_user where id=#{id}")
    public User findById(Long id);

}
