package com.hugo.blog.dao;

import com.hugo.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);

}
