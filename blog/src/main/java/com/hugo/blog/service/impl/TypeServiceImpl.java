package com.hugo.blog.service.impl;

import com.hugo.blog.NotFoundException;
import com.hugo.blog.dao.ITypeRepository;

import com.hugo.blog.po.Type;
import com.hugo.blog.service.ITypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: blog
 * @description: 分页管理
 * @author: KaiDo
 * @return:
 * @create: 2020-01-25 18:08
 **/
@Service
public class TypeServiceImpl implements ITypeService {


    @Autowired
    private ITypeRepository iTypeRepository;

    @Transactional
    @Override
    public void deleteType(Long id) {
        iTypeRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {

        return iTypeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Type save(Type type) {

        return iTypeRepository.save(type);
    }


    @Transactional
    @Override
    public Type getType(Long id) {

        return iTypeRepository.getOne(id);
    }

    /**
     * @Description: 修改
     */
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t1 = iTypeRepository.getOne(id);
        if (t1 == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type, t1);
        return iTypeRepository.save(t1);
    }

    @Override
    public Type getTypeByName(String name) {
        return iTypeRepository.findByName(name);
    }

    @Override
    public List<Type> listType() {
       return iTypeRepository.findAll();
    }
    
    /** 
    * @Description: 分类排行
    */
    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return iTypeRepository.findTop(pageable);
    }
}

