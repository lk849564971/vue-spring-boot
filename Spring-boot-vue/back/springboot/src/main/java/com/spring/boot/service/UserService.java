package com.spring.boot.service;

import com.spring.boot.dao.UserDAO;
import com.spring.boot.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    //查找一个用户
    public User findOne(String uname) {
        return userDAO.findByUname(uname).get(0);
    }

    //添加用户
    public void addUser(User user) {
        userDAO.save(user);
    }
}
