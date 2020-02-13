package com.hugo.blog.dao;

import com.hugo.blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICommentRepository extends JpaRepository<Comment,Long> { //实体类型，主键类型

    //获取父级评论
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);

}
