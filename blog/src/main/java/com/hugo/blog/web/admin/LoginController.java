package com.hugo.blog.web.admin;

import com.hugo.blog.po.User;
import com.hugo.blog.service.IUserService;
import com.hugo.blog.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @program: blog
 * @description: 后台管理登录Controller
 * @author: KaiDo
 * @create: 2020-01-21 23:56
 **/
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private IUserService iUserService;

    /**
    * @Description: 跳转登录页面
    * @Author: Mr.KaiDo
    * @Date: 2020/1/22
    */
    @GetMapping
    public String loginPage(){

        return "admin/login";
    }

    /**
    * @Description: 用户登录
    * @Author: Mr.KaiDo
    * @Date: 2020/1/22
    */
    @PostMapping("/login")
    public  String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes){
            User user = iUserService.checkUser(username,password);
            if(user != null){
                user.setPassword(null);
                session.setAttribute("user",user);
                return "admin/index";
            }else{
                redirectAttributes.addFlashAttribute("message","用户名或密码错误");
                return "redirect:/admin";
            }

        }

    /** 
    * @Description: 注销用户
    * @Author: Mr.KaiDo
    * @Date: 2020/1/22 
    */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
