package com.hugo.blog.web;

import com.hugo.blog.NotFoundException;
import com.hugo.blog.po.Blog;
import com.hugo.blog.service.IBlogService;
import com.hugo.blog.service.ITagService;
import com.hugo.blog.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @program: blog
 * @description: 首页，详情
 * @author: Mr.HUHO
 * @create: 2020-01-13 17:29
 **/
@Controller
public class IndexController {

    @Autowired
    private IBlogService iBlogService;
    @Autowired
    private ITypeService iTypeService;
    @Autowired
    private ITagService iTagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("page",iBlogService.listBlog(pageable));  //首页-博客分页数据
        model.addAttribute("types",iTypeService.listTypeTop(6)); //首页-博客分类排序数据
        model.addAttribute("tags",iTagService.listTagTop(10)); //首页-博客标签排序数据
        model.addAttribute("recommendsBlog",iBlogService.listRecommendBlogTop(8)); //首页-博客推荐数排序数据
        return "index";
    }

    //博客详情展示
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",iBlogService.getAndConvert(id));
        return "blog";
    }

    
    /** 
    * @Description: 搜索 
    */
    @PostMapping("/search")
    public String search(@PageableDefault(size = 3,sort ={"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         String query,Model model ){
        model.addAttribute("page",iBlogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    /**
    * @Description: 关于我
    */
    @GetMapping("/about")
    public String aboutShow(){
        return "about";
    }

    /**
    * @Description: 页面底部动态更新
    */
    @GetMapping("/footer/footerList")
    public String footerShow(Model model){
        model.addAttribute("newBlogs",iBlogService.listRecommendBlogTop(3));
        return "_fragments :: blogFooterList";
    }
}
