package com.hugo.blog.po;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="t_tag")
/**
 * 标签实体类
 */
public class Tag {

    @Id//主键字段
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "tags")  //关联，被维护,懒加载
    private List<Blog> blogs = new ArrayList<>();

}
