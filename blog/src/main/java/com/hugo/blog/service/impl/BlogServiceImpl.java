package com.hugo.blog.service.impl;

import com.hugo.blog.NotFoundException;
import com.hugo.blog.dao.IBlogRepository;
import com.hugo.blog.po.Blog;
import com.hugo.blog.po.Type;
import com.hugo.blog.service.IBlogService;
import com.hugo.blog.util.MarkdownUtils;
import com.hugo.blog.util.MyBeanUtils;
import com.hugo.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @program: blog
 * @description:
 * @author: KaiDo
 * @create: 2020-01-28 20:28
 **/
@Service
public class BlogServiceImpl implements IBlogService {

    @Autowired
    private IBlogRepository iBlogRepository;

    /**
    * @Description: 保存
    */
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId()==null){     //新增时初始化
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
        }else{
            blog.setUpdateTime(new Date());
        }
        return iBlogRepository.save(blog);
    }

    /**
     * @Description: 修改
     */
    @Override
    @Transactional
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1 = iBlogRepository.getOne(id);   //查询已有的blog对象
        if(blog1 == null){
            throw new NotFoundException("博客不存在");
        }
        BeanUtils.copyProperties(blog,blog1,MyBeanUtils.getNullPropertyNames(blog)); //利用写好的工具类，只复制blog中有值的数据
        blog1.setUpdateTime(new Date());
        return iBlogRepository.save(blog1);
    }

    /**
    * @Description: 删除
    */
    @Transactional
    @Override
    public void deletaBlog(Long id) {

        iBlogRepository.deleteById(id);
    }


    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
       Blog b = iBlogRepository.getOne(id);
       if(b == null){
           throw new NotFoundException("该博客不存在");
       }
       Blog b2 = new Blog();
       BeanUtils.copyProperties(b,b2);
       //将markdown转换为html
       b2.setContent(MarkdownUtils.markdownToHtmlExtensions(b2.getContent()));
       iBlogRepository.updateViews(id);
       return b2;
    }

    @Override
    public Blog getBlog(Long id) {
        return iBlogRepository.getOne(id);
    }

    /**
    * @Description: 分页动态查询
    */
    @Override
    public Page<Blog> listBlog( Pageable pageable,BlogQuery bq) {
        return iBlogRepository.findAll(new Specification<Blog>() {
            @Override
                public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cQ, CriteriaBuilder cB) {
                List<Predicate> predicates = new ArrayList<>();
                if(!"".equals(bq.getTitle()) && bq.getTitle() != null ){
                    predicates.add(cB.like(root.<String>get("title"),"%"+bq.getTitle()+"%"));
                }
                if(bq.getTypeId() != null){
                    predicates.add(cB.equal(root.<Type>get("type").get("id"),bq.getTypeId()));
                }
                if(bq.isRecommend()){
                    predicates.add(cB.equal(root.<Boolean>get("recommend"),bq.isRecommend()));
                }
                cQ.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);

    }
    
    /** 
    * @Description: 获取分页数据 
    */
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return iBlogRepository.findAll(pageable);
    }

    /**
    * @Description: Tag关联Blog查询
    */
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return iBlogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags"); //建立关联
                return cb.equal(join.get("id"),tagId);  //查询是否与tagId相等
            }
        },pageable);
    }

    /** 
    * @Description: 博首页客-推荐排序展示 
    */
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return iBlogRepository.findTop(pageable);
    }
    
    /** 
    * @Description: 博客模糊查询 
    */
    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return iBlogRepository.findByQuery(query,pageable);
    }

    @Override
    public LinkedHashMap<String, List<Blog>> archivesBlog() {
        List<String> years = iBlogRepository.findGroupYear();
        LinkedHashMap<String, List<Blog>> map = new LinkedHashMap<>();
        for (String year : years) {
            map.put(year,iBlogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return iBlogRepository.count();
    }

}
