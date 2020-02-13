package com.hugo.blog.web.admin;

import com.hugo.blog.po.Type;
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
 * @description: 分类
 * @author: KaiDo
 * @return:
 * @create: 2020-01-26 14:47
 **/
@RequestMapping("/admin")
@Controller
public class TypeController {

    @Autowired
    private ITypeService iTypeService;

    /**
    * @Description:分类页面
    */
    @GetMapping("/types")
    public String Types(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable,
                                    Model model){
        model.addAttribute("page",iTypeService.listType(pageable));
        return "admin/types";
    }
    
    /** 
    * @Description: 分类编辑，根据id传给前台要被编辑的分类
    */
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("type",iTypeService.getType(id));
        return "admin/types-input";
    }

    /**
    * @Description: 返回新增页面
    */
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    /**
    * @Description: 从页面接收提交的新增分类,后端非空校验
    */
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result,
                       RedirectAttributes redirectAttributes){
        Type type2 = iTypeService.getTypeByName(type.getName());
        if(type2 != null){
            result.rejectValue("name","nameError","该名称重复");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type type1 = iTypeService.save(type);
        if(type1 == null){
            //没保存成功时
            redirectAttributes.addFlashAttribute("message","添加失败");
        }else{
            redirectAttributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/types";
    }

    /**
    * @Description: 编辑
    */
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,
                       @PathVariable Long id, RedirectAttributes redirectAttributes){
        Type type2 = iTypeService.getTypeByName(type.getName());
        if(type2 != null){
            result.rejectValue("name","nameError","不能添加重复分类");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type type1 = iTypeService.updateType(id,type);
        if(type1 == null){
            //没保存成功时
            redirectAttributes.addFlashAttribute("message","更新失败");
        }else{
            redirectAttributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id,RedirectAttributes redirectAttributes){
        iTypeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
