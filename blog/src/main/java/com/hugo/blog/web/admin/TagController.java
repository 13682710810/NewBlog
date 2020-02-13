package com.hugo.blog.web.admin;

import com.hugo.blog.po.Tag;
import com.hugo.blog.po.Type;
import com.hugo.blog.service.ITagService;
import com.hugo.blog.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @program: blog
 * @description: 标签管理
 * @author: KaiDo
 * @return:
 * @create: 2020-01-26 14:47
 **/
@RequestMapping("/admin")
@Controller
public class TagController {

    @Autowired
    private ITagService iTagService;

    /**
    * @Description:标签页面
    */
    @GetMapping("/tags")
    public String Tags(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable,
                                    Model model){
        model.addAttribute("page",iTagService.listTag(pageable));
        return "admin/tags";
    }
    
    /** 
    * @Description: 标签编辑，根据id传给前台要被编辑的标签
    */
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("tag",iTagService.getTag(id));
        return "admin/tags-input";
    }

    /**
    * @Description: 返回新增页面
    */
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    /**
    * @Description: 从页面接收提交的新增标签
    */
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result,
                       RedirectAttributes redirectAttributes){
        Tag tag2 = iTagService.getTagByName(tag.getName());
        if(tag2 != null){
            result.rejectValue("name","nameError","该名称重复");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag tag1 = iTagService.save(tag);
        if(tag1 == null){
            //没保存成功时
            redirectAttributes.addFlashAttribute("message","添加失败");
        }else{
            redirectAttributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/tags";
    }

    /**
    * @Description: 编辑
    */
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result,
                       @PathVariable Long id, RedirectAttributes redirectAttributes){
        Tag tag3= iTagService.getTagByName(tag.getName());
        if(tag3 != null){
            result.rejectValue("name","nameError","不能添加重复标签");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag tag4 = iTagService.updateTag(id,tag);
        if(tag4 == null){
            //没保存成功时
            redirectAttributes.addFlashAttribute("message","更新失败");
        }else{
            redirectAttributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id,RedirectAttributes redirectAttributes){
        iTagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }
}
