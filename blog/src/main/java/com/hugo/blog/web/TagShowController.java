package com.hugo.blog.web;

import com.hugo.blog.po.Tag;
import com.hugo.blog.po.Type;
import com.hugo.blog.service.IBlogService;
import com.hugo.blog.service.ITagService;
import com.hugo.blog.service.ITypeService;
import com.hugo.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @program: blog
 * @description: 标签页展示
 * @author: KaiDo
 * @return:
 * @create: 2020-02-08 22:59
 **/
@Controller
public class TagShowController {

    @Autowired
    private IBlogService iBlogService;

    @Autowired
    private ITagService itagService;


    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 3,sort ={"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model ){
        List<Tag> tags = itagService.listTagTop(10000); //查询标签并排序
        if(id == -1){   //如果为-1，则是在导航页点击标签
            id = tags.get(0).getId();    //获取排序第一的标签id
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page",iBlogService.listBlog(id,pageable));
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
