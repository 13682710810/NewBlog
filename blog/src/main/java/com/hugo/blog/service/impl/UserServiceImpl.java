package com.hugo.blog.service.impl;

import com.hugo.blog.dao.IUserRepository;
import com.hugo.blog.po.User;
import com.hugo.blog.service.IUserService;
import com.hugo.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: blog
 * @description: IUserServie实现类
 * @author: KaiDo
 * @create: 2020-01-21 23:48
 **/
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository iUserRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = iUserRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
