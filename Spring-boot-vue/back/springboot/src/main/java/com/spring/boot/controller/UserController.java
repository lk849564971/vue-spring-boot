package com.spring.boot.controller;


import com.spring.boot.bean.User;
import com.spring.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController //直接返回JSON数据
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    //登录
    @RequestMapping("/login")
    public String login(@RequestParam(value = "uname") String uname,
                        @RequestParam(value = "upwd") String upwd) throws Exception {
        try {
            User user = userService.findOne(uname);
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
            User user = new User();
            user.setUname(uname);
            user.setUpwd(upwd);
            userService.addUser(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
