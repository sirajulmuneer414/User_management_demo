package com.week9.spring.loginproject.controller;

import com.week9.spring.loginproject.entity.Users;
import com.week9.spring.loginproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class AppController {


    public String authenticatingLogin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            if(roles.contains("ADMIN")){
                return "redirect:/admin/admin-panel";
            }
            if(roles.contains("USER")){
                return "redirect:/user/userDashboard";
            }
        }
        return "redirect:/user/userDashboard";
    }
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String signUp(@RequestParam(name="message" ,required = false) String param , Model model){
        if(param!=null)
            model.addAttribute("error",true);
        return "signup";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute Users user){
        if(userService.doUserEmailExists(user.getUseremail())){
            return "redirect:/register?emailError";
        }
        if(userService.doUserNameExists(user.getUsername())){
            return "redirect:/register?userNameError";
        }
        userService.saveUser(user);
        return "redirect:/index?message=success";
    }

    @GetMapping("/login")
    public String login(){
        return authenticatingLogin();
    }

    @GetMapping("/signup")
    public String signup(){ return "login";}

    @GetMapping("/index")
    public String home(@RequestParam(name="message",required = false) String param , Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if(roles.contains("ADMIN")){
                return "redirect:/admin/admin-panel";
            }
            if(roles.contains("USER")){
                return "redirect:/user/userDashboard";
            }
        }
        if(param!=null)
            model.addAttribute("success",true);
        return "index";
    }

    @GetMapping("/")
    public String def(){
        return "redirect:/index";
    }


}