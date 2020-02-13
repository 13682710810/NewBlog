package com.hugo.blog.service;


import com.hugo.blog.po.Tag;
import com.hugo.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ITagService {

    /**
    * @Description: 删除
    */
    void deleteTag(Long id);

    /**
    * @Description: 分页查询
    */
    Page<Tag> listTag(Pageable pageable);

    /**
    * @Description: 保存
    */
    Tag save(Tag tag);

    /**
    * @Description: 根据id查询标签
    */
    Tag getTag(Long id);

    /**
    * @Description: 修改
    */
    Tag updateTag(Long id, Tag tag);

    /**
    * @Description: 根据名称获取标签，作校验用
    */
    Tag getTagByName(String name);

    /**
    * @Description: 查询所有
    */
    List<Tag> listTag();

    /**
    * @Description: 获取ids(逗号隔开的多个id)
    */
    List<Tag> listTag(String ids);

    /**
    * @Description: 首页标签排序
    */
    List<Tag> listTagTop(Integer size);
}
