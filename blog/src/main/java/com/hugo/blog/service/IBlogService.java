package com.hugo.blog.service;

import com.hugo.blog.po.Blog;
import com.hugo.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface IBlogService {

     //@Description: 添加
    Blog saveBlog(Blog blog);

     //@Description: 删除
    void deletaBlog(Long id);

    //@Description:查询markdown并转换
    Blog getAndConvert(Long id);

    //@Description: 更新
    Blog updateBlog(Long id,Blog blog);

    // @Description: 查询
    Blog getBlog(Long id);

     // @Description: 列表查询
    Page<Blog> listBlog( Pageable pageable,BlogQuery bq);

     // @Description: 首页博客分页
    Page<Blog> listBlog(Pageable pageable);

    // @Description: 根据标签tagId，关联blog查询
    Page<Blog> listBlog(Long id,Pageable pageable);

     //@Description: 首页博客推荐（按照更新时间排序）
    List<Blog> listRecommendBlogTop(Integer size);

     //@Description: 博客详情
    Page<Blog> listBlog(String query, Pageable pageable);

    //@Description: 博客归档，根据年份归档
    LinkedHashMap<String, List<Blog>> archivesBlog();

    //@Description: 博客数目
    Long countBlog();
}
