package com.hugo.blog.service.impl;

import com.hugo.blog.dao.ICommentRepository;
import com.hugo.blog.po.Comment;
import com.hugo.blog.service.ICommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: blog
 * @description: 评论管理
 * @author: KaiDo
 * @return:
 * @create: 2020-02-05 21:05
 **/
@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private ICommentRepository iCommentRepository;
    
    //处理顶级评论
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = new Sort(Sort.Direction.ASC,"createTime");
        List<Comment> comments = iCommentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
        return eachComments(comments);
    }
    
    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long ParentCommentId = comment.getParentComment().getId();
        if(ParentCommentId !=-1){       //如果是回复的话
            comment.setParentComment(iCommentRepository.getOne(ParentCommentId));
        }else{                          //如果不是回复
            comment.setParentComment(null); //避免ParentCommentId为-1时报错
        }
        comment.setCreateTime(new Date());
        return iCommentRepository.save(comment);
    }
    
    /** 
    * @Description: 循环每个顶级评论 ,参数为ParentCommentId为null的顶级评论
    */
    private List<Comment> eachComments(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c =new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //将顶级评论的各层子评论合并到同一集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /** 
    * @Description: 将所有子类节点存放在顶级节点的reply集合 
    */
    private void combineChildren(List<Comment> comments){
        for (Comment comment : comments) {
           List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                recursively(reply);       //从二级评论开始迭代，将子级评论存于临时集合tempReplys
            }
            comment.setReplyComments(tempReplys);//将临时集合存于replys集合中
            tempReplys = new ArrayList<>(); //清空临时集合
        }
    }


    //临时存放迭代找出的所有子类的集合
     private List<Comment> tempReplys  = new ArrayList<Comment>();

    /**
    * @Description: 迭代找出所有子类节点
    */
    private void  recursively(Comment comment){
        tempReplys.add(comment); //添加二级评论
        if(comment.getReplyComments().size()>0){    //如果二级评论还有子评论，就循环迭代二级之下的评论
            List<Comment> replyComments = comment.getReplyComments();
            for (Comment replyComment : replyComments) {
                tempReplys.add(replyComment);   //将子集添加到临时集合
                if(replyComment.getReplyComments().size()>0){
                    recursively(comment);
                }
            }
        }
    }
}
