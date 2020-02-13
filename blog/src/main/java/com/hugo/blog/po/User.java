package com.hugo.blog.po;

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
@Table(name = "t_user")
/**
 * 用户实体类
 */
public class User {

    @Id//主键字段
    @GeneratedValue
    private Long id;

    private String nickName;    //昵称
    private String username;
    private String password;
    private String email;
    private String avatar;  //头像
    private Integer type;   //1代表管理员

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)   //关联，被维护,懒加载
    private List<Blog> blogs= new ArrayList<>();
}