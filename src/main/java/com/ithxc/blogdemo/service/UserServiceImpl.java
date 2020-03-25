package com.ithxc.blogdemo.service;

import com.ithxc.blogdemo.bean.User;
import com.ithxc.blogdemo.mapper.UserMapper;
import com.ithxc.blogdemo.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hxc
 * @create 2020-03-08 22:21
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {
        User user = userMapper.login(username, MD5Utils.code(password));
        return user;
    }
}
