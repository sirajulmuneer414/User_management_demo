package com.week9.spring.loginproject.controller;

import com.week9.spring.loginproject.entity.Users;
import com.week9.spring.loginproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class adminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/admin-panel")
    public String adminPanel(Model model, @RequestParam(name = "search",required = false) String keyword){
        if(keyword!=null) {
            model.addAttribute("users", userService.listUsersStartingWith(keyword));
        }
        else {
            model.addAttribute("users", userService.listOfUsers());
        }
        return "admin/admin-panel";
    }
    @GetMapping("/admin/admin-panel/Update/{id}")
    public String update(Model model , @PathVariable("id") long id){
        model.addAttribute("user",userService.findUserById(id));
        System.out.println(userService.findUserById(id));
        return "admin/update-user";
    }

    @PostMapping("/admin/admin-panel/Update/save/{id}")
    public String saveChanges(@PathVariable("id") long id,@ModelAttribute Users user){
        if(userService.doUserEmailExists(user.getUseremail())&&!user.getUseremail().equals(userService.findUserById(id).getUseremail())){

            return "redirect:/admin/admin-panel/Update/{id}?emailError";
        }
        if(userService.doUserNameExists(user.getUsername())&&!user.getUsername().equals(userService.findUserById(id).getUsername())){
            return "redirect:/admin/admin-panel/Update/{id}?userNameError";
        }
        userService.updateById(id,user);

        return "redirect:/admin/admin-panel?update=User+is+updated!";
    }

    @PostMapping("/admin/admin-panel/Delete/{id}")
    public String delete(Model model , @PathVariable("id") long id) {
        userService.deleteUserById(id);

        return "redirect:/admin/admin-panel?delete=User+deleted!";
    }
    @GetMapping("/admin/admin-panel/user-creation")
    public String adminCreation(){
        return "admin/user-create";
    }

    @PostMapping("/admin/admin-panel/create-user")
    public String createAdmin(@ModelAttribute Users user,Model model){
        if(userService.doUserEmailExists(user.getUseremail())){

            return "redirect:/admin/admin-panel/user-creation?emailError";
        }
        if(userService.doUserNameExists(user.getUsername())){
            return "redirect:/admin/admin-panel/user-creation?userNameError";
        }
        userService.saveUser(user);
        model.addAttribute("userCreated",true);
        return "redirect:/admin/admin-panel?admin=User+created!";
    }

}
