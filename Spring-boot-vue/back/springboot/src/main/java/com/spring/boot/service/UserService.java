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

    public Users findOne(String uname) {
        return userDAO.findByUname(uname).get(0);
    }

    public void addUser(Users user) {
        userDAO.save(user);
    }
}
