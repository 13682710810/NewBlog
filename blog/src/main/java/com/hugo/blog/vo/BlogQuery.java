package com.hugo.blog.vo;

import com.hugo.blog.po.Blog;
import com.hugo.blog.po.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: blog
 * @description: 封装类 博客标题，分类，推荐
 * @author: KaiDo
 * @return:
 * @create: 2020-01-30 17:12
 **/
@Setter
@Getter
public class BlogQuery {

    private String title;

    private Long typeId;

    private boolean recommend;
}
