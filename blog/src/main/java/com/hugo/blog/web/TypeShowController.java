package com.hugo.blog.web;

import com.hugo.blog.po.Type;
import com.hugo.blog.service.IBlogService;
import com.hugo.blog.service.ITypeService;
import com.hugo.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @program: blog
 * @description: 分类页展示
 * @author: KaiDo
 * @return:
 * @create: 2020-02-08 22:59
 **/
@Controller
public class TypeShowController {

    @Autowired
    private IBlogService iBlogService;

    @Autowired
    private ITypeService iTypeService;


    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 3,sort ={"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model ){
        List<Type> types = iTypeService.listTypeTop(10000); //查询分类并排序
        if(id == -1){   //如果为-1，则是在导航点击分类
            id = types.get(0).getId();    //获取排序第一的分类id
        }
        BlogQuery blogQuery =new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",iBlogService.listBlog(pageable,blogQuery));
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
