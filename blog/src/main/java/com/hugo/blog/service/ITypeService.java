package com.hugo.blog.service;


import com.hugo.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITypeService {

    /**
    * @Description: 删除
    */
    void deleteType(Long id);

    /**
    * @Description: 分页查询
    */
    Page<Type> listType(Pageable pageable);

    /**
    * @Description: 保存
    */
    Type save(Type type);

    /**
    * @Description: 根据id查询分类
    */
    Type getType(Long id);
    
    /** 
    * @Description: 修改
    */
    Type updateType(Long id,Type type);

    /**
    * @Description: 根据名称获取分类，作校验用
    */
    Type getTypeByName(String name);
    
    /** 
    * @Description: 查询所有
    */
    List<Type> listType();
    
    /** 
    * @Description: 首页展示，分类数目排行 
    */
    List<Type> listTypeTop(Integer size);
}
