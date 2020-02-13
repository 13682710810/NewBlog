package com.hugo.blog.web;

import com.hugo.blog.po.Blog;
import com.hugo.blog.po.Comment;
import com.hugo.blog.po.User;
import com.hugo.blog.service.IBlogService;
import com.hugo.blog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @program: blog
 * @description: 评论模块
 * @author: KaiDo
 * @return:
 * @create: 2020-02-05 18:30
 **/
@Controller
public class commentController {

    @Autowired
    private ICommentService iCommentService;

    @Autowired
    private IBlogService iBlogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",iCommentService.listCommentByBlogId(blogId));
        return "blog::commentList"; //局部刷新评论列表区域
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        Long blogId = comment.getBlog().getId();
        Blog blog = iBlogService.getBlog(blogId);
        User user =(User)session.getAttribute("user");
        if(user != null){   //判断管理员是否登录
            comment.setAdminComment(true);
            comment.setAvatar(user.getAvatar());
        }else{
            comment.setAvatar(avatar);
        }
       iCommentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
