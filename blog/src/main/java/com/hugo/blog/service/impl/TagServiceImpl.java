package com.hugo.blog.service.impl;

import com.hugo.blog.NotFoundException;
import com.hugo.blog.dao.ITagRepository;
import com.hugo.blog.dao.ITypeRepository;
import com.hugo.blog.po.Tag;
import com.hugo.blog.po.Type;
import com.hugo.blog.service.ITagService;
import com.hugo.blog.service.ITypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: blog
 * @description: 标签管理
 * @author: KaiDo
 * @return:
 * @create: 2020-01-25 18:08
 **/
@Service
public class TagServiceImpl implements ITagService {


    @Autowired
    private ITagRepository iTagRepository;

    @Transactional
    @Override
    public void deleteTag(Long id) {
        iTagRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return iTagRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Tag save(Tag tag) {
        return iTagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return iTagRepository.getOne(id);
    }



    /**
    * @Description: 修改
    */
    @Transactional
    @Override
    public Tag updateTag(Long id, Tag type) {
        Tag t1 = iTagRepository.getOne(id);
        if(t1 == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t1);
        return iTagRepository.save(t1);
    }

    @Override
    public Tag getTagByName(String name) {
        return iTagRepository.findByName(name);
    }

    @Override
    public List<Tag> listTag() {
        return iTagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {  
        return iTagRepository.findAllById(convertToList(ids));
    }

    /**
     * @Description: 首页标签排序
     */

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return iTagRepository.findTop(pageable);
    }

    /** 
    * @Description: 将字符串转换为数组
    */
    private List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids != null){
            String[] idarray = ids.split(",");
            for(int i =0;i<idarray.length;i++){
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }


}
