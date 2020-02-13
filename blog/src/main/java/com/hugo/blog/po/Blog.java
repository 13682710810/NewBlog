package com.hugo.blog.po;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "t_blog")

/**
 * 博客实体类
 */
public class Blog {

    @Id //主键字段
    @GeneratedValue
    private Long id;

    private String title; //博客标题

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content; //博客内容
    private String firstPicture; //首图
    private String flag; //原创/转载/翻译
    private Integer views=0;  //浏览次数
    private String description; //博客简介
    private boolean appreciation;   //赞赏
    private boolean shareStatement; //转载声明
    private boolean commentabled;   //评论(不开启)
    private boolean published;  //1：保存  0：发布
    private boolean recommend;  //是否推荐


    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @ManyToOne
    private Type type;  //分类
    @ManyToOne
    private User user;  //用户
    @ManyToMany(cascade = {CascadeType.PERSIST}) //级联持久化
    private List<Tag> tags=new ArrayList<>();   //标签
    @OneToMany(mappedBy = "blog")   //关联，被维护s
    private List<Comment> comments=new ArrayList<>();
    @Transient
    private String tagIds;

    private String tagsToIdS(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuffer ids=new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if(flag){
                    ids.append(",");
                }else{
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else{
            return tagIds;
        }
    }

    public void init(){
        this.tagIds = tagsToIdS(this.getTags());
    }

}
