package com.hugo.blog.service;

import com.hugo.blog.po.User;

public interface IUserService {

    User checkUser(String username, String password);
}
