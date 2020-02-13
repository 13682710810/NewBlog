package com.hugo.blog.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="t_comment")
/**
 * 评论实体类
 */
public class Comment {

    @Id//主键字段
    @GeneratedValue
    private Long id;

    private String nickname;    //昵称
    private String email;   //邮箱

    @NotEmpty(message = "内容不能为空")
    @Size(max=500,message = "评论内容不能多于500个字")
    @Column(nullable = false)
    private String content; //内容

    private String avatar;  //头像

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne
    private Blog blog;

    private Boolean adminComment;

    /**
     **评论类自关联（评论回复功能）
     */
    @OneToMany(mappedBy = "parentComment")
    private List<Comment>  replyComments=new ArrayList<>();

    @ManyToOne
    private Comment parentComment;
    
}
