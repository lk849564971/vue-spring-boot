package com.spring.boot.service;

import com.spring.boot.dao.UserDAO;
import com.spring.boot.bean.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserDAO userDAO;

    //查找一个用户
    public Users findOne(String uname) {
        return userDAO.findByUname(uname).get(0);
    }

    //添加用户
    public void addUser(Users user) {
        userDAO.save(user);
    }
}
