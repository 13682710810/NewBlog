package com.hugo.blog.dao;

import com.hugo.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* @Description:     继承JpaSpecificationExecutor，提供高级查询。
*/
public interface IBlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommend=true")
    List<Blog> findTop(Pageable pageable);

    //使用的是like，需要在传query时处理，加上“% %”
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id =?1")
    int updateViews(Long id);

    @Query("select function('date_format',b.updateTime,'%Y')as Y from Blog b GROUP BY function('date_format',b.updateTime,'%Y') order by Y DESC ")
    List<String> findGroupYear();

    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') like ?1")
    List<Blog> findByYear(String year);

}
