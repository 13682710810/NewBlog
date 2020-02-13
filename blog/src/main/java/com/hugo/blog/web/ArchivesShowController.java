package com.hugo.blog.web;

import com.hugo.blog.po.Blog;
import com.hugo.blog.service.IBlogService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * @program: blog
 * @description: 归档
 * @author: KaiDo
 * @return:
 * @create: 2020-02-09 22:53
 **/
@Controller
public class ArchivesShowController {

    @Autowired
    private IBlogService iBlogService;

    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archiveMap",iBlogService.archivesBlog());
        model.addAttribute("blogCount",iBlogService.countBlog());
        return "archives";
    }
}
