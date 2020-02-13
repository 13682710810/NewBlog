package com.hugo.blog.web.admin;

import com.hugo.blog.po.Blog;
import com.hugo.blog.po.Type;
import com.hugo.blog.po.User;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @program: blog
 * @description:
 * @author: KaiDo
 * @return:
 * @create: 2020-01-24 18:08
 **/
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private IBlogService iBlogService;
    @Autowired
    private ITypeService iTypeService;
    @Autowired
    private ITagService iTagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 3,sort ={"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery bq,Model model ){
        model.addAttribute("types",iTypeService.listType());
        model.addAttribute("page",iBlogService.listBlog(pageable,bq));
        return "admin/blogs";
    }


    /**
    * @Description: 局部渲染（标题，分类，是否推荐）
    */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 3,sort ={"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model model,
                         BlogQuery bq){
        model.addAttribute("page",iBlogService.listBlog(pageable,bq));
        return "admin/blogs :: blogList";
    }
    
    /** 
    * @Description: 跳转新增页面,页面渲染
    */
    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }

    private void setTypeAndTag(Model model){
        model.addAttribute("types",iTypeService.listType());
        model.addAttribute("tags",iTagService.listTag());
    }

    /**
    * @Description: 编辑博客
    */
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog =iBlogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return "admin/blogs-input";
    }

    /**
    * @Description: 新增时候的提交
    */
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes redirectAttributes, HttpSession session){
        blog.setUser((User)session.getAttribute("user"));
        blog.setType(iTypeService.getType(blog.getType().getId()));
        blog.setTags(iTagService.listTag(blog.getTagIds()));
        Blog bg;
        //前端传来的blog某些数据为空，需进行处理。
        if(blog.getId()==null ){
            bg = iBlogService.saveBlog(blog);
        }else{
            bg = iBlogService.updateBlog(blog.getId(),blog);
        }
        if(bg == null ){
            //没保存成功时
            redirectAttributes.addFlashAttribute("message","操作失败");
        }else{
            redirectAttributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id,RedirectAttributes redirectAttributes){
        iBlogService.deletaBlog(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }
}
