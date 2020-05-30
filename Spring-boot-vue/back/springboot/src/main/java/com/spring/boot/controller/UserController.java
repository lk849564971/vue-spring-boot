package com.spring.boot.controller;


import com.spring.boot.bean.Users;
import com.spring.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@Controller
@RestController // RestController 直接返回JSON数据
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    //登录
    @RequestMapping("/login")
    public String login(@RequestParam(value = "uname") String uname,
                        @RequestParam(value = "upwd") String upwd) throws Exception {
        try {
            Users user = userService.findOne(uname);
            if (user.getUpwd().compareTo(upwd)==0) {
                return String.valueOf(user.getUno());
            }
        }
        catch (Exception e) {
            return "false";
        }
        return "false";
    }


    //注册
    @RequestMapping("/register")
    public Boolean register(@RequestParam(value = "uname") String uname,
                            @RequestParam(value = "upwd") String upwd) throws Exception {
        try {
            Users users = new Users();
            users.setUname(uname);
            users.setUpwd(upwd);
            userService.addUser(users);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
