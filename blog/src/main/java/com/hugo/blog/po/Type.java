package com.hugo.blog.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="t_type")
/**
 * 分类实体类
 */
public class Type {

    @Id//主键字段
    @GeneratedValue
    private Long id;

    @NotBlank(message = "分类名称不能为空") //搭配TypeController后端双重校验
    private String name;

    @OneToMany(mappedBy = "type")   //关联，被维护
    private List<Blog> blogs= new ArrayList<>();

}
