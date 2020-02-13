package com.hugo.blog;

import com.hugo.blog.service.IBlogService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private IBlogService iBlogService;

    @Test
    void contextLoads() {
        System.out.println(iBlogService.archivesBlog());
    }

}
