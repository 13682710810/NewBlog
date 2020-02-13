package com.hugo.blog.service;

import com.hugo.blog.po.Comment;

import java.util.List;


public interface ICommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
