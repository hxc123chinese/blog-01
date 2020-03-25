package com.ithxc.blogdemo.service;

import com.ithxc.blogdemo.bean.User;

/**
 * @author hxc
 * @create 2020-03-08 22:19
 */

public interface UserService {

    User checkUser(String username,String password);
}
